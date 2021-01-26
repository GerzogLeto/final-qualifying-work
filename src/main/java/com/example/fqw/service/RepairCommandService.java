package com.example.fqw.service;

import com.example.fqw.entity.RefuelCommand;
import com.example.fqw.entity.RepairCommand;
import com.example.fqw.exception.RefuelCommandException;
import com.example.fqw.exception.RepairCommandException;
import com.example.fqw.exception.TruckException;
import com.example.fqw.logic.CommandTypes;
import com.example.fqw.logic.DefaultProperties;
import com.example.fqw.logic.StatusCommand;
import com.example.fqw.repository.RepairCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RepairCommandService {
    @Autowired
    private RepairCommandRepository repository;
    @Autowired
    private TruckService truckService;

    private static final int TIME_REPAIR =
            Integer.parseInt(new DefaultProperties().
                    getProperties().getProperty("time_repair"));

    public RepairCommand add(RepairCommand command){
        if(repository.existsById(command.getId()))
            throw new RepairCommandException("Запись уже существует.");
        if(!(command.getCommandType().equals(CommandTypes.REPAIR))){
            throw new RepairCommandException("Некорректный тип команды");
        }
        if(command.getTimeStart().isBefore(LocalDateTime.now().plusHours(1))){
            throw new RepairCommandException("Некорректное время старта команды");
        }
        try{
            truckService.getById(command.getIdTruck());
        }catch (TruckException e){
            throw new RepairCommandException("Грузовик с указанным ИД не существует");
        }
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeFinish(command.getTimeStart().plusHours(TIME_REPAIR));
        RepairCommand saved = repository.save(command);
        return saved;
    }

    public RepairCommand update(RepairCommand command){
        if(!repository.existsById(command.getId())){
            throw new RepairCommandException("Запись не существует");
        }
        if(!(command.getCommandType().equals(CommandTypes.REPAIR))){
            throw new RepairCommandException("Некорректный тип команды");
        }
        if(command.getTimeStart().isBefore(LocalDateTime.now().plusHours(1))){
            throw new RepairCommandException("Некорректное время старта команды");
        }
        try{
            truckService.getById(command.getIdTruck());
        }catch (TruckException e){
            throw new RepairCommandException("Грузовик с указанным ИД не существует");
        }
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeFinish(command.getTimeStart().plusHours(TIME_REPAIR));
        return repository.save(command);
    }

    public RepairCommand getCommandsByIdTruckByStatusCurrent(long idTruck, StatusCommand status) {
        Optional<RepairCommand> optional = repository.findByIdTruckAndStatusCurrent(idTruck, status);
        if (optional.isEmpty()) return null;
        return optional.get();
    }

    public Iterable<RepairCommand> getCommandsByIdTruckByStatus(long idTruck, StatusCommand status){
        Iterable<RepairCommand> commands = repository.
                findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, status);
        return commands;
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new RepairCommandException("Запись не существует");
        repository.deleteById(id);
    }
}

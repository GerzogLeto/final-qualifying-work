package com.example.fqw.service;

import com.example.fqw.entity.GoToCommand;
import com.example.fqw.entity.RefuelCommand;
import com.example.fqw.exception.GoToCommandException;
import com.example.fqw.exception.RefuelCommandException;
import com.example.fqw.exception.TruckException;
import com.example.fqw.logic.CommandTypes;
import com.example.fqw.logic.DefaultProperties;
import com.example.fqw.logic.StatusCommand;
import com.example.fqw.repository.RefuelCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefuelCommandService {
    @Autowired
    private RefuelCommandRepository repository;
    @Autowired
    private TruckService truckService;

    private static final int REFUEL_DURATION =
            Integer.parseInt(new DefaultProperties().
                    getProperties().getProperty("refueling_duration"));

    public RefuelCommand add(RefuelCommand command){
        if(repository.existsById(command.getId()))
            throw new RefuelCommandException("Запись уже существует.");
        if(!(command.getCommandType().equals(CommandTypes.REFUEL))){
            throw new RefuelCommandException("Некорректный тип команды");
        }
        if(command.getTimeStart().isBefore(LocalDateTime.now().plusHours(1))){
            throw new RefuelCommandException("Некорректное время старта команды");
        }
        try{
            truckService.getById(command.getIdTruck());
        }catch (TruckException e){
            throw new RefuelCommandException("Грузовик с указанным ИД не существует");
        }
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeFinish(command.getTimeStart().plusMinutes(REFUEL_DURATION));
        RefuelCommand saved = repository.save(command);
        return saved;
    }

    public RefuelCommand update(RefuelCommand command){
        if(!repository.existsById(command.getId())){
            throw new RefuelCommandException("Запись не существует");
        }
        if(!(command.getCommandType().equals(CommandTypes.REFUEL))){
            throw new RefuelCommandException("Некорректный тип команды");
        }
        if(command.getTimeStart().isBefore(LocalDateTime.now().plusHours(1))){
            throw new RefuelCommandException("Некорректное время старта команды");
        }
        try{
            truckService.getById(command.getIdTruck());
        }catch (TruckException e){
            throw new RefuelCommandException("Грузовик с указанным ИД не существует");
        }
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeFinish(command.getTimeStart().plusMinutes(REFUEL_DURATION));
        return repository.save(command);
    }

    public RefuelCommand getCommandsByIdTruckByStatusCurrent(long idTruck, StatusCommand status) {
        Optional<RefuelCommand> optional = repository.findByIdTruckAndStatusCurrent(idTruck, status);
        if (optional.isEmpty()) return null;
        return optional.get();
    }

    public Iterable<RefuelCommand> getCommandsByIdTruckByStatus(long idTruck, StatusCommand status){
        Iterable<RefuelCommand> commands = repository.
                findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, status);
        return commands;
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new RefuelCommandException("Запись не существует");
        repository.deleteById(id);
    }
}

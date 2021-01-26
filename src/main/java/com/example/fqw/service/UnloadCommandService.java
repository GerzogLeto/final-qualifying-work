package com.example.fqw.service;

import com.example.fqw.entity.UnloadCommand;
import com.example.fqw.exception.FreightException;
import com.example.fqw.exception.LoadCommandException;
import com.example.fqw.exception.TruckException;
import com.example.fqw.exception.UnloadCommandException;
import com.example.fqw.logic.CommandTypes;
import com.example.fqw.logic.StatusCommand;
import com.example.fqw.repository.FreightRepository;
import com.example.fqw.repository.UnloadCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UnloadCommandService {
    @Autowired
    private UnloadCommandRepository repository;

    @Autowired
    private FreightRepository freightRepository;
    @Autowired
    private TruckService truckService;
    @Autowired
    private FreightService freightService;

    public UnloadCommand add(UnloadCommand command){
        if(repository.existsById(command.getId()))
            throw new UnloadCommandException("Запись уже существует.");
        if(!(command.getCommandType().equals(CommandTypes.UNLOAD))){
            throw new UnloadCommandException("Некорректный тип команды");
        }
        try{
            truckService.getById(command.getIdTruck());
            freightService.getById(command.getIdFreight());
        }catch (TruckException e){
            throw new UnloadCommandException("Грузовик с указанным ИД не существует");
        }catch (FreightException e){
            throw new UnloadCommandException("Груз с указанным ИД не существует");
        }
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeStart(freightRepository.findById(command.getIdFreight()).get().getTimeOfUnloadingCargo());
        command.setTimeFinish(command.getTimeStart().
                plusHours(freightRepository.findById(command.getIdFreight()).get().getDurationOfUnloadingCargo()));
        UnloadCommand saved = repository.save(command);
        return saved;
    }

    public UnloadCommand update(UnloadCommand command){
        if(!repository.existsById(command.getId())){
            throw new UnloadCommandException("Запись не существует");
        }
        if(!(command.getCommandType().equals(CommandTypes.UNLOAD))){
            throw new UnloadCommandException("Некорректный тип команды");
        }
        try{
            truckService.getById(command.getIdTruck());
            freightService.getById(command.getIdFreight());
        }catch (TruckException e){
            throw new UnloadCommandException("Грузовик с указанным ИД не существует");
        }catch (FreightException e){
            throw new UnloadCommandException("Груз с указанным ИД не существует");
        }
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeStart(freightRepository.findById(command.getIdFreight()).get().getTimeOfUnloadingCargo());
        command.setTimeFinish(command.getTimeStart().
                plusHours(freightRepository.findById(command.getIdFreight()).get().getDurationOfUnloadingCargo()));
        return repository.save(command);
    }

    public UnloadCommand getCommandsByIdTruckByStatusCurrent(long idTruck, StatusCommand status) {
        Optional<UnloadCommand> optional = repository.findByIdTruckAndStatusCurrent(idTruck, status);
        if (optional.isEmpty()) return null;
        return optional.get();
    }

    public Iterable<UnloadCommand> getCommandsByIdTruckByStatus(long idTruck, StatusCommand status){
        Iterable<UnloadCommand> commands = repository.
                findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, status);
        return commands;
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new UnloadCommandException("Запись не существует");
        repository.deleteById(id);
    }


}

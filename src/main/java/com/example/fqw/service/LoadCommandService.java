package com.example.fqw.service;

import com.example.fqw.entity.LoadCommand;
import com.example.fqw.entity.RepairCommand;
import com.example.fqw.exception.LoadCommandException;
import com.example.fqw.exception.RepairCommandException;
import com.example.fqw.logic.StatusCommand;
import com.example.fqw.repository.FreightRepository;
import com.example.fqw.repository.LoadCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoadCommandService {
    @Autowired
    private LoadCommandRepository repository;
    @Autowired
    private FreightRepository freightRepository;

    public LoadCommand add(LoadCommand command){
        if(repository.existsById(command.getId()))
            throw new LoadCommandException("Запись уже существует.");
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeStart(freightRepository.findById(command.getIdFreight()).get().getTimeOfLoadingCargo());
        command.setTimeFinish(command.getTimeStart().
                plusHours(freightRepository.findById(command.getIdFreight()).get().getDurationOfLoadingCargo()));
        freightRepository.findById(command.getIdFreight()).get().setOpen(false);
        LoadCommand saved = repository.save(command);
        return saved;
    }

    public LoadCommand update(LoadCommand command){
        if(!repository.existsById(command.getId())){
            throw new LoadCommandException("Запись не существует");
        }
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeStart(freightRepository.findById(command.getIdFreight()).get().getTimeOfLoadingCargo());
        command.setTimeFinish(command.getTimeStart().
                plusHours(freightRepository.findById(command.getIdFreight()).get().getDurationOfLoadingCargo()));
        freightRepository.findById(command.getIdFreight()).get().setOpen(false);
        return repository.save(command);
    }

    public LoadCommand getCommandsByIdTruckByStatusCurrent(long idTruck, StatusCommand status) {
        Optional<LoadCommand> optional = repository.findByIdTruckAndStatusCurrent(idTruck, status);
        if (optional.isEmpty()) return null;
        return optional.get();
    }

    public Iterable<LoadCommand> getCommandsByIdTruckByStatus(long idTruck, StatusCommand status){
        Iterable<LoadCommand> commands = repository.
                findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, status);
        return commands;
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new LoadCommandException("Запись не существует");
        repository.deleteById(id);
    }
}

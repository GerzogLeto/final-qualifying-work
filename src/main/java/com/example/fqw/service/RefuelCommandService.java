package com.example.fqw.service;

import com.example.fqw.entity.RefuelCommand;
import com.example.fqw.exception.RefuelCommandException;
import com.example.fqw.logic.DefaultProperties;
import com.example.fqw.logic.StatusCommand;
import com.example.fqw.repository.RefuelCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefuelCommandService {
    @Autowired
    private RefuelCommandRepository repository;

    private static final int REFUEL_DURATION =
            Integer.parseInt(new DefaultProperties().
                    getProperties().getProperty("refueling_duration"));

    public RefuelCommand add(RefuelCommand command){
        if(repository.existsById(command.getId()))
            throw new RefuelCommandException("Запись уже существует.");
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeFinish(command.getTimeStart().plusMinutes(REFUEL_DURATION));
        RefuelCommand saved = repository.save(command);
        return saved;
    }

    public RefuelCommand update(RefuelCommand command){
        if(!repository.existsById(command.getId())){
            throw new RefuelCommandException("Запись не существует");
        }
        command.setStatusCommand(StatusCommand.FUTURE);
        command.setTimeFinish(command.getTimeStart().plusMinutes(REFUEL_DURATION));
        return repository.save(command);
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new RefuelCommandException("Запись не существует");
        repository.deleteById(id);
    }
}

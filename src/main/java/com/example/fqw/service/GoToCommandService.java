package com.example.fqw.service;

import com.example.fqw.entity.GoToCommand;
import com.example.fqw.entity.Truck;
import com.example.fqw.exception.GoToCommandException;
import com.example.fqw.exception.TruckException;
import com.example.fqw.logic.DefaultProperties;
import com.example.fqw.logic.StatusCommand;
import com.example.fqw.repository.GoToCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoToCommandService {
    @Autowired
    private GoToCommandRepository repository;

    public GoToCommand add(GoToCommand command){
        if(repository.existsById(command.getId()))
            throw new GoToCommandException("Запись уже существует.");
        command.setStatusCommand(StatusCommand.FUTURE);
        GoToCommand saved = repository.save(command);
        return saved;
    }

    public GoToCommand update(GoToCommand goToCommand){
        if(!repository.existsById(goToCommand.getId())){
            throw new GoToCommandException("Запись не существует");
        }
        goToCommand.setStatusCommand(StatusCommand.FUTURE);
        return repository.save(goToCommand);
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new GoToCommandException("Запись не существует");
        repository.deleteById(id);
    }
}

package com.example.fqw.service;

import com.example.fqw.entity.Position;
import com.example.fqw.exception.PositionException;
import com.example.fqw.repository.PositionRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Getter
    @Autowired
    private PositionRepository repository;

    public Position add(Position position){
        if(repository.existsById(position.getId())){
            throw new PositionException("Запись уже существует");
        }
        return repository.save(position);
    }

}

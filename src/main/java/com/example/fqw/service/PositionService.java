package com.example.fqw.service;

import com.example.fqw.entity.Position;
import com.example.fqw.exception.PositionException;
import com.example.fqw.repository.PositionRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PositionService {
    @Autowired
    private PositionRepository repository;

    public Position add(Position position){
        if(repository.existsById(position.getId())){
            throw new PositionException("Запись уже существует");
        }
        if(position.getStart().length()<3){
            throw new PositionException("В названии города меньше 3 букв");
        }
        if(position.getFinish().length()<3){
            throw new PositionException("В названии города меньше 3 букв");
        }
        if(position.getDistFromStart() < 0){
            throw new PositionException("Расстояние от точки старта меньше 0!");
        }
        if(position.getTotalDist() <= 0){
            throw new PositionException("Общая дистанция меньше или равна 0!");
        }
        if(position.getDistFromStart() > position.getTotalDist()){
            throw new PositionException("Расстояние от точки старта больше общей дистанции!");
        }
        return repository.save(position);
    }

    public Optional<Position> getById(long id) {
        Optional<Position> optionalPosition = repository.findById(id);
        if (optionalPosition.isEmpty()) throw new PositionException("Запись не существует");
        return optionalPosition;
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new PositionException("Запись не существует");
        repository.deleteById(id);
    }

    public Position update(Position position){
        if(!repository.existsById(position.getId())){
            throw new PositionException("Запись не существует");
        }
        if(position.getStart().length()<3){
            throw new PositionException("В названии города меньше 3 букв");
        }
        if(position.getFinish().length()<3){
            throw new PositionException("В названии города меньше 3 букв");
        }
        if(position.getDistFromStart() < 0){
            throw new PositionException("Расстояние от точки старта меньше 0!");
        }
        if(position.getTotalDist() <= 0){
            throw new PositionException("Общая дистанция меньше или равна 0!");
        }
        if(position.getDistFromStart() > position.getTotalDist()){
            throw new PositionException("Расстояние от точки старта больше общей дистанции!");
        }
        return repository.save(position);
    }

}

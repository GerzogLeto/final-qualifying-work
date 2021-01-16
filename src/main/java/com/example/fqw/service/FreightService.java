package com.example.fqw.service;

import com.example.fqw.entity.Freight;
import com.example.fqw.entity.Position;
import com.example.fqw.exception.FreightException;
import com.example.fqw.exception.PositionException;
import com.example.fqw.repository.FreightRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreightService {
    @Autowired
    private FreightRepository repository;

    public Freight add(Freight freight){
        if(repository.existsById(freight.getId()))
            throw new FreightException("Запись уже существует.");
        return repository.save(freight);
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new PositionException("Запись не существует");
        repository.deleteById(id);
    }

    public Freight update(Freight freight){
        if(!repository.existsById(freight.getId())){
            throw new FreightException("Запись не существует");
        }
        return repository.save(freight);
    }
}

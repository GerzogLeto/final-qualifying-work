package com.example.fqw.service;

import com.example.fqw.entity.Freight;
import com.example.fqw.exception.FreightException;
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
        System.out.println("--serializing--");
        ObjectMapper om = new ObjectMapper();
        try {
            System.out.println(om.writeValueAsString(freight));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(repository.existsById(freight.getId()))
            throw new FreightException("Запись уже существует.");
        return repository.save(freight);
    }
}

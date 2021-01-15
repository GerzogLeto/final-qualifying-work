package com.example.fqw.controller;

import com.example.fqw.entity.Freight;
import com.example.fqw.exception.FreightException;
import com.example.fqw.service.FreightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/freights")
public class FreightController {
    @Autowired
    private FreightService service;
    @PostMapping
    public Freight addFreight (@RequestBody Freight freight){
        Freight saved;
        try {
            saved = service.add(freight);
        } catch (FreightException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
        return saved;
    }


}

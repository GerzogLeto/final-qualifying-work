package com.example.fqw.controller;

import com.example.fqw.entity.Position;
import com.example.fqw.exception.PositionException;
import com.example.fqw.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/positions")
public class PositionController {
    @Autowired
    private PositionService service;

    @PostMapping
    public Position addPosition (@RequestBody Position position) {
        Position saved;
        try {
            saved = service.add(position);
        } catch (PositionException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
        return saved;
    }
}

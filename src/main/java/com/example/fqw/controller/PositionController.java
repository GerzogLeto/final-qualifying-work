package com.example.fqw.controller;

import com.example.fqw.entity.Position;
import com.example.fqw.exception.PositionException;
import com.example.fqw.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
//@CrossOrigin
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

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            service.delete(id);
        } catch (PositionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping
    public Position updatePosition(@RequestBody Position position) {
        Position updated;
        try {
            updated = service.update(position);
        } catch (PositionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        return updated;
    }

    @GetMapping("/{id}")
    public Position getById(@PathVariable int id) {
        Optional<Position> position;
        try {
            position = service.getById(id);
        } catch (PositionException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return position.get();
    }
}

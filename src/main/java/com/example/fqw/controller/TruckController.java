package com.example.fqw.controller;

import com.example.fqw.entity.Freight;
import com.example.fqw.entity.Truck;
import com.example.fqw.exception.FreightException;
import com.example.fqw.exception.PositionException;
import com.example.fqw.exception.TruckException;
import com.example.fqw.service.FreightService;
import com.example.fqw.service.PositionService;
import com.example.fqw.service.TruckService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/trucks")
public class TruckController {

    @Autowired
    private TruckService service;
    @Autowired
    private PositionService positionService;
    @Autowired
    private FreightService freightService;
    @Autowired
    private ObjectMapper mapper;

    @PostMapping
    public Truck addTruck (@RequestParam(value = "truck") String truck,
                             @RequestParam(value = "positionId") long positionId){
        Truck truckFromJSON;
        Truck saved = null;
        try {
            truckFromJSON = mapper.readValue(truck, Truck.class);
            truckFromJSON.setPosition(positionService.getById(positionId).get());            saved = service.add(truckFromJSON);
        } catch (TruckException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saved;
    }

    @PutMapping
    public Truck updateTruck (@RequestParam(value = "truck") String truck,
                           @RequestParam(value = "positionId") long positionId){
        Truck truckFromJSON;
        Truck updated = null;
        try {
            truckFromJSON = mapper.readValue(truck, Truck.class);
            truckFromJSON.setPosition(positionService.getById(positionId).get());
            updated = service.update(truckFromJSON);
        } catch (TruckException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            service.delete(id);
        } catch (TruckException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}

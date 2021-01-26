package com.example.fqw.controller;

import com.example.fqw.entity.Freight;
import com.example.fqw.entity.Truck;
import com.example.fqw.exception.FreightException;
import com.example.fqw.exception.PositionException;
import com.example.fqw.exception.TruckException;
import com.example.fqw.service.FreightService;
import com.example.fqw.service.PositionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/freights")
public class FreightController {
    @Autowired
    private FreightService service;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ObjectMapper mapper;
    @PostMapping
    public Freight addFreight (@RequestParam(value = "freight") String freight,
                               @RequestParam(value = "loadingId") long loadingId,
                               @RequestParam(value = "unloadingId") long unloadingId){
        Freight freightFromJSON;
        Freight saved = null;
        try {
            freightFromJSON = mapper.readValue(freight, Freight.class);
            freightFromJSON.setPlaceOfLoadingCargo(positionService.getById(loadingId).get());
            freightFromJSON.setPlaceOfUnloadingCargo(positionService.getById(unloadingId).get());
            saved = service.add(freightFromJSON);
        } catch (FreightException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saved;
    }
    @PutMapping
    public Freight updateFreight (@RequestParam(value = "freight") String freight,
                               @RequestParam(value = "loadingId") long loadingId,
                               @RequestParam(value = "unloadingId") long unloadingId){
        Freight freightFromJSON;
        Freight updated = null;
        try {
            freightFromJSON = mapper.readValue(freight, Freight.class);
            freightFromJSON.setPlaceOfLoadingCargo(positionService.getById(loadingId).get());
            freightFromJSON.setPlaceOfUnloadingCargo(positionService.getById(unloadingId).get());
            updated = service.update(freightFromJSON);
        } catch (FreightException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @GetMapping //http://localhost:8080/freights?city=Moscow
    public Iterable<Freight> getByLoadPlace(@RequestParam String city) {
        Iterable<Freight> freights;
        try {
            freights  = service.findByLoadPlace(city);
        } catch (FreightException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return freights;
    }

    @GetMapping
    public Iterable<Freight> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Freight getById(@PathVariable int id) {
        Optional<Freight> optionalFreight;
        try {
            optionalFreight = service.getById(id);
        } catch (FreightException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return optionalFreight.get();
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            service.delete(id);
        } catch (PositionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


}

package com.example.fqw.controller;

import com.example.fqw.entity.GoToCommand;
import com.example.fqw.entity.LoadCommand;
import com.example.fqw.entity.RefuelCommand;
import com.example.fqw.entity.RepairCommand;
import com.example.fqw.exception.GoToCommandException;
import com.example.fqw.exception.LoadCommandException;
import com.example.fqw.exception.RefuelCommandException;
import com.example.fqw.exception.RepairCommandException;
import com.example.fqw.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/command-manager")
public class CommandManagerController {
    @Autowired
    private GoToCommandService goToCommandService;
    @Autowired
    private RefuelCommandService refuelCommandService;
    @Autowired
    private RepairCommandService repairCommandService;
    @Autowired
    private LoadCommandService loadCommandService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/go-to-command")
    public GoToCommand addGoToCommand(@RequestParam(value = "command") String command,
                                        @RequestParam(value = "destination_id") long destination_id){
        GoToCommand commandFromJSON;
        GoToCommand saved = null;
        try{
            commandFromJSON = mapper.readValue(command, GoToCommand.class);
            commandFromJSON.setPosFinish(positionService.getById(destination_id).get());
            saved = goToCommandService.add(commandFromJSON);
        }catch (GoToCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saved;
    }
    @PostMapping("/refuel")
    public RefuelCommand addRefuelCommand(@RequestParam(value = "command") String command){
        RefuelCommand saved = null;
        try{
            saved = refuelCommandService.add(mapper.readValue(command, RefuelCommand.class));
        }catch (RefuelCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saved;
    }

    @PostMapping("/repair")
    public RepairCommand addRepairCommand(@RequestParam(value = "command") String command){
        RepairCommand saved = null;
        try{
            saved = repairCommandService.add(mapper.readValue(command, RepairCommand.class));
        }catch (RepairCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saved;
    }

    @PostMapping("/load")
    public LoadCommand addLoadCommand(@RequestParam(value = "command") String command){
        LoadCommand saved = null;
        try{
            saved = loadCommandService.add(mapper.readValue(command, LoadCommand.class));
        }catch (LoadCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saved;
    }


    @PutMapping("/go-to-command")
    public GoToCommand updateGoToCommand(@RequestParam(value = "command") String command,
                                         @RequestParam(value = "destination_id") long destination_id){
        GoToCommand commandFromJSON;
        GoToCommand updated = null;
        try{
            commandFromJSON = mapper.readValue(command, GoToCommand.class);
            commandFromJSON.setPosFinish(positionService.getById(destination_id).get());
            updated = goToCommandService.update(commandFromJSON);
        }catch (GoToCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @PutMapping("/refuel")
    public RefuelCommand updateRefuelCommand(@RequestParam(value = "command") String command){
        RefuelCommand updated = null;
        try{
            updated = refuelCommandService.update(mapper.readValue(command, RefuelCommand.class));
        }catch (RefuelCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @PutMapping("/repair")
    public RepairCommand updateRepairCommand(@RequestParam(value = "command") String command){
        RepairCommand updated = null;
        try{
            updated = repairCommandService.update(mapper.readValue(command, RepairCommand.class));
        }catch (RepairCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @PutMapping("/load")
    public LoadCommand updateLoadCommand(@RequestParam(value = "command") String command){
        LoadCommand updated = null;
        try{
            updated = loadCommandService.update(mapper.readValue(command, LoadCommand.class));
        }catch (LoadCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @DeleteMapping("/go-to-command/{id}")
    public void deleteByIdGoToCommand(@PathVariable int id) {
        try {
            goToCommandService.delete(id);
        } catch (GoToCommandException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/refuel/{id}")
    public void deleteByIdRefuelCommand(@PathVariable int id) {
        try {
            refuelCommandService.delete(id);
        } catch (RefuelCommandException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/repair/{id}")
    public void deleteByIdRepairCommand(@PathVariable int id) {
        try {
            repairCommandService.delete(id);
        } catch (RefuelCommandException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/load/{id}")
    public void deleteByIdLoadCommand(@PathVariable int id) {
        try {
            repairCommandService.delete(id);
        } catch (LoadCommandException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}

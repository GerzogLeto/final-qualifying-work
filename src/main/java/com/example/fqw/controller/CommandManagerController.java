package com.example.fqw.controller;

import com.example.fqw.entity.Freight;
import com.example.fqw.entity.GoToCommand;
import com.example.fqw.exception.GoToCommandException;
import com.example.fqw.exception.TruckException;
import com.example.fqw.service.GoToCommandService;
import com.example.fqw.service.PositionService;
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
    private PositionService positionService;
    @Autowired
    private ObjectMapper mapper;
    @PostMapping("/go-to-command")
    public GoToCommand addCommand(@RequestParam(value = "command") String command,
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
    @PutMapping("/go-to-command")
    public GoToCommand updateCommand(@RequestParam(value = "command") String command,
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

    @DeleteMapping("/go-to-command/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            goToCommandService.delete(id);
        } catch (GoToCommandException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}

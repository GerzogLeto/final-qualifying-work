package com.example.fqw.controller;

import com.example.fqw.entity.Freight;
import com.example.fqw.entity.GoToCommand;
import com.example.fqw.service.GoToCommandService;
import com.example.fqw.service.PositionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saved;
    }
}

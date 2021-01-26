package com.example.fqw.controller;

import com.example.fqw.entity.*;
import com.example.fqw.exception.*;
import com.example.fqw.logic.ICommand;
import com.example.fqw.logic.StatusCommand;
import com.example.fqw.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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
    private UnloadCommandService unloadCommandService;
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
        } catch(InvalidFormatException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }catch (JsonMappingException e) {
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
        }catch(InvalidFormatException e) {
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
        }catch(InvalidFormatException e) {
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
        }catch(InvalidFormatException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saved;
    }

    @PostMapping("/unload")
    public UnloadCommand addUnloadCommand(@RequestParam(value = "command") String command){
        UnloadCommand saved = null;
        try{
            saved = unloadCommandService.add(mapper.readValue(command, UnloadCommand.class));
        }catch (UnloadCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }catch(InvalidFormatException e) {
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
        }catch(InvalidFormatException e) {
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
        }catch(InvalidFormatException e) {
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
        }catch(InvalidFormatException e) {
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
        }catch(InvalidFormatException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @PutMapping("/unload")
    public UnloadCommand updateUnloadCommand(@RequestParam(value = "command") String command){
        UnloadCommand updated = null;
        try{
            updated = unloadCommandService.update(mapper.readValue(command, UnloadCommand.class));
        }catch (UnloadCommandException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }catch(InvalidFormatException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return updated;
    }
    @GetMapping //http://localhost:8080/command-manager?idTruck=1&status=current
    public Iterable<ICommand> getCommandsByIdTruckByStatus(@RequestParam long idTruck,
                                                           @RequestParam String status){
        List<ICommand> commands = new ArrayList<>();
        if(status.toLowerCase().equals(StatusCommand.CURRENT.name().toLowerCase())){
            ICommand command = goToCommandService.getCommandsByIdTruckByStatusCurrent(idTruck, StatusCommand.CURRENT);
            if(!(command == null)){
                commands.add(command);
                return commands ;
            }
            command = refuelCommandService.getCommandsByIdTruckByStatusCurrent(idTruck, StatusCommand.CURRENT);
            if(!(command == null)){
                commands.add(command);
                return commands ;
            }
            command = repairCommandService.getCommandsByIdTruckByStatusCurrent(idTruck, StatusCommand.CURRENT);
            if(!(command == null)){
                commands.add(command);
                return commands ;
            }
            command = loadCommandService.getCommandsByIdTruckByStatusCurrent(idTruck, StatusCommand.CURRENT);
            if(!(command == null)){
                commands.add(command);
                return commands ;
            }
            command = unloadCommandService.getCommandsByIdTruckByStatusCurrent(idTruck, StatusCommand.CURRENT);
            if(!(command == null)){
                commands.add(command);
                return commands ;
            }
        }
        for (GoToCommand goToCommand :
                goToCommandService.getCommandsByIdTruckByStatus(idTruck,
                        StatusCommand.valueOf(status.toUpperCase()))) {
            commands.add(goToCommand);
        }

        for (RefuelCommand refuelCommand :
                refuelCommandService.getCommandsByIdTruckByStatus(idTruck,
                        StatusCommand.valueOf(status.toUpperCase()))) {
            commands.add(refuelCommand);
        }

        for (RepairCommand repairCommand :
                repairCommandService.getCommandsByIdTruckByStatus(idTruck,
                        StatusCommand.valueOf(status.toUpperCase()))) {
            commands.add(repairCommand);
        }

        for (LoadCommand loadCommand :
                loadCommandService.getCommandsByIdTruckByStatus(idTruck,
                        StatusCommand.valueOf(status.toUpperCase()))) {
            commands.add(loadCommand);
        }

        for (UnloadCommand unloadCommand :
                unloadCommandService.getCommandsByIdTruckByStatus(idTruck,
                        StatusCommand.valueOf(status.toUpperCase()))) {
            commands.add(unloadCommand);
        }
        return commands;

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
            loadCommandService.delete(id);
        } catch (LoadCommandException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/unload/{id}")
    public void deleteByIdUnloadCommand(@PathVariable int id) {
        try {
            repairCommandService.delete(id);
        } catch (UnloadCommandException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}

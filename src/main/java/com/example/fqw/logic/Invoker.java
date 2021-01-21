package com.example.fqw.logic;

import com.example.fqw.entity.GoToCommand;
import com.example.fqw.entity.Truck;
import com.example.fqw.repository.GoToCommandRepository;
import com.example.fqw.repository.TruckRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class Invoker {
    @Getter
    @Setter
    private Truck truck;
    @Getter
    @Setter
    private ICommand command;
    @Getter
    @Setter
    private DefaultProperties defaultProperties;
    @Getter
    @Setter
    private TruckRepository truckRepo;
    @Getter
    @Setter
    private GoToCommandRepository goToCommandRepo;

    public void execute(){
        switch (command.getCommandType()){
            case GOTO -> {
                GoToCommand goToCommand = (GoToCommand)command;

            }
        }
    }

    public void initCurrentCommand(){
        switch (command.getCommandType()){
            case GOTO -> {
                GoToCommand goToCommand = (GoToCommand)command;
                goToCommand.setPosStart(truck.getPosition());
                goToCommand.setDistance();
                goToCommand.setUpDuration(Integer.parseInt(defaultProperties.
                        getProperties().getProperty("speed")));
                goToCommand.setTimeFinish(goToCommand.getTimeStart().plusMinutes(goToCommand.getDuration()));
                goToCommand.setStatusCommand(StatusCommand.CURRENT);
                goToCommandRepo.save(goToCommand);
            }

        }
    }



}

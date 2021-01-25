package com.example.fqw.logic;

import com.example.fqw.entity.*;
import com.example.fqw.repository.*;
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
    private FreightRepository freightRepo;
    @Getter
    @Setter
    private GoToCommandRepository goToCommandRepo;
    @Getter
    @Setter
    private RefuelCommandRepository refuelCommandRepo;
    @Getter
    @Setter
    private RepairCommandRepository repairCommandRepo;
    @Getter
    @Setter
    private LoadCommandRepository loadCommandRepo;

    public void changeStateTruck(){
        switch (command.getCommandType()){
            case GOTO -> {
                GoToCommand goToCommand = (GoToCommand)command;
                truck.setAmountOfFuel((int)(truck.getAmountOfFuel() -
                        (Double.parseDouble(defaultProperties.getProperties().getProperty("fuel_cons")) *
                                goToCommand.getDistance())));
                truck.setMileage(truck.getMileage() + goToCommand.getDistance());
                truck.setPosition(goToCommand.getPosFinish());
                truckRepo.save(truck);
                goToCommand.setStatusCommand(StatusCommand.ARCHIVE);
                goToCommandRepo.save(goToCommand);
                break;


            }

            case REFUEL -> {
                RefuelCommand refCommand = (RefuelCommand)command;
                truck.setAmountOfFuel(truck.getFuelTankCapacity());
                truckRepo.save(truck);
                refCommand.setStatusCommand(StatusCommand.ARCHIVE);
                refuelCommandRepo.save(refCommand);
                break;
            }

            case REPAIR -> {
                RepairCommand repCommand = (RepairCommand)command;
                truck.setMileageNextRepair(truck.getMileageNextRepair() +
                        Integer.parseInt(defaultProperties.getProperties().getProperty("mileage_between_repairs")));
                truckRepo.save(truck);
                repCommand.setStatusCommand(StatusCommand.ARCHIVE);
                repairCommandRepo.save(repCommand);
                break;
            }

            case LOAD -> {
                LoadCommand loadCommand = (LoadCommand)command;
                truck.setFreight(freightRepo.findById(loadCommand.getIdFreight()).get());
                truckRepo.save(truck);
                loadCommand.setStatusCommand(StatusCommand.ARCHIVE);
                loadCommandRepo.save(loadCommand);
                break;
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
                break;
            }
            case REFUEL -> {
                RefuelCommand refCommand = (RefuelCommand)command;
                refCommand.setStatusCommand(StatusCommand.CURRENT);
                refuelCommandRepo.save(refCommand);
                break;
            }
            case REPAIR -> {
                RepairCommand repCommand = (RepairCommand)command;
                repCommand.setStatusCommand(StatusCommand.CURRENT);
                repairCommandRepo.save(repCommand);
                break;
            }
            case LOAD -> {
                LoadCommand loadCommand = (LoadCommand)command;
                loadCommand.setStatusCommand(StatusCommand.CURRENT);
                loadCommandRepo.save(loadCommand);
                break;
            }

        }
    }



}

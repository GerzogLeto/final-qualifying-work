package com.example.fqw.thread;

import com.example.fqw.entity.*;
import com.example.fqw.logic.DefaultProperties;
import com.example.fqw.logic.ICommand;
import com.example.fqw.logic.Invoker;
import com.example.fqw.logic.StatusCommand;
import com.example.fqw.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
//@Scope("prototype")
public class ThreadTask implements Runnable {
    @Autowired
    private GoToCommandRepository goToCommandRepository;
    @Autowired
    private RefuelCommandRepository refuelCommandRepository;
    @Autowired
    private RepairCommandRepository repairCommandRepository;
    @Autowired
    private LoadCommandRepository loadCommandRepository;
    @Autowired
    private TruckRepository truckRepository;

    @Override
    public void run() {
        for (Truck truck : truckRepository.findAll()) {
            ICommand command;
            if (!((command = findCurrentCommand(truck.getId())) == null)) {
                if(checkTimeFinish(command.getTimeFinish())){
                    System.out.println("Truck: " + truck.getName() +" ### " + truck.getNumber() +
                            " ### " + truck.getId() + " the current command " +
                            command.getCommandType() + " is being executed now");
                    continue;
                }else{
                    System.out.println("Truck: " + truck.getName() +" ### " + truck.getNumber() +
                            " ### " + truck.getId() + " the current command " +
                            command.getCommandType() + " has been completed. State needs to be changed");
                    //отправить тек комманду на выполнение для измен состояния грузовика
                    //сделать вып команду архивной
                    Invoker invoker = new Invoker();
                    invoker.setDefaultProperties(new DefaultProperties());
                    invoker.setTruck(truck);
                    invoker.setCommand(command);
                    invoker.setTruckRepo(this.truckRepository);
                    invoker.setGoToCommandRepo(this.goToCommandRepository);
                    invoker.setRefuelCommandRepo(this.refuelCommandRepository);
                    invoker.setRepairCommandRepo(this.repairCommandRepository);
                    invoker.setLoadCommandRepo(this.loadCommandRepository);
                    invoker.changeStateTruck();


                }

            }else {
                System.out.println("Truck: " + truck.getName() +" ### " + truck.getNumber() +
                        " ### " + truck.getId() + " no current command ");
                //найти команду на роль текущей по таймингу
                //доинициализировать комманду
                //сделать команду текущей


                command = getNextCurrentCommand(truck.getId());
                if(command==null){
                    System.out.println("Truck: " + truck.getName() +" ### " + truck.getNumber() +
                            " ### " + truck.getId() + " there is no suitable command for the current role");

                }
                else{
                    System.out.println("Truck: " + truck.getName() +" ### " + truck.getNumber() +
                            " ### " + truck.getId() + " catching command for the current role");

                    Invoker invoker = new Invoker();
                    invoker.setDefaultProperties(new DefaultProperties());
                    invoker.setTruck(truck);
                    invoker.setCommand(command);
                    invoker.setTruckRepo(this.truckRepository);
                    invoker.setGoToCommandRepo(this.goToCommandRepository);
                    invoker.setRefuelCommandRepo(this.refuelCommandRepository);
                    invoker.setRepairCommandRepo(this.repairCommandRepository);
                    invoker.setLoadCommandRepo(this.loadCommandRepository);
                    invoker.initCurrentCommand();
                }
            }
        }

    }

    public boolean checkTimeFinish(LocalDateTime finish) {
        return finish.isAfter(LocalDateTime.now().plusHours(1));
    }

    public boolean checkTimeStart(LocalDateTime start) {
        return start.isAfter(LocalDateTime.now().plusHours(1));
    }

    public ICommand findCurrentCommand(long id) {
        Optional<GoToCommand> goToCommand = goToCommandRepository.findByIdTruckAndStatusCurrent(id,
                StatusCommand.CURRENT);
        Optional<RefuelCommand> refuelCommand = refuelCommandRepository.findByIdTruckAndStatusCurrent(id,
                StatusCommand.CURRENT);
        Optional<RepairCommand> repairCommand = repairCommandRepository.findByIdTruckAndStatusCurrent(id,
                StatusCommand.CURRENT);
        Optional<LoadCommand> loadCommand = loadCommandRepository.findByIdTruckAndStatusCurrent(id,
                StatusCommand.CURRENT);
        if (!(goToCommand.isEmpty())) return goToCommand.get();
        if (!(refuelCommand.isEmpty())) return refuelCommand.get();
        if (!(repairCommand.isEmpty())) return repairCommand.get();
        if (!(loadCommand.isEmpty())) return loadCommand.get();
        return null;
    }

    public ICommand getNextCurrentCommand(long idTruck){
        for (GoToCommand goToCommand :
                goToCommandRepository.findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, StatusCommand.FUTURE)) {
            if(!(checkTimeStart(goToCommand.getTimeStart()))){
                return goToCommand;
            }
        }
        for (RefuelCommand refuelCommand :
                refuelCommandRepository.findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, StatusCommand.FUTURE)) {
            if(!(checkTimeStart(refuelCommand.getTimeStart()))){
                return refuelCommand;
            }
        }
        for (RepairCommand repairCommand :
                repairCommandRepository.findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, StatusCommand.FUTURE)) {
            if(!(checkTimeStart(repairCommand.getTimeStart()))){
                return repairCommand;
            }
        }
        for (LoadCommand loadCommand :
                loadCommandRepository.findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, StatusCommand.FUTURE)) {
            if(!(checkTimeStart(loadCommand.getTimeStart()))){
                return loadCommand;
            }
        }
        return null;
    }


}

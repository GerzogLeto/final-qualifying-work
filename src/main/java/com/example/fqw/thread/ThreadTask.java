package com.example.fqw.thread;

import com.example.fqw.entity.*;
import com.example.fqw.logic.*;
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
    private UnloadCommandRepository unloadCommandRepository;
    @Autowired
    private TruckRepository truckRepository;
    @Autowired
    private FreightRepository freightRepository;

    @Override
    public void run() {
        for (Truck truck : truckRepository.findAll()) {
            ICommand command;
            if (!((command = findCurrentCommand(truck.getId())) == null)) {
                if(checkTimeFinish(command.getTimeFinish())){
                    MyLogger.defineInfoMessage(InfoType.CURRENT_COMMAND, new Object[]{
                            truck.getId(), truck.getName(), truck.getNumber(), command.getCommandType().name()});
                    continue;
                }else{
                    MyLogger.defineInfoMessage(InfoType.CURRENT_COMMAND_COMPLETED, new Object[]{
                            truck.getId(), truck.getName(), truck.getNumber(), command.getCommandType().name()});
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
                    invoker.setUnloadCommandRepo(this.unloadCommandRepository);
                    invoker.setFreightRepo(this.freightRepository);
                    invoker.changeStateTruck();


                }

            }else {
                MyLogger.defineInfoMessage(InfoType.NOT_CURRENT_COMMAND, new Object[]{
                        truck.getId(), truck.getName(), truck.getNumber(), null});
                //найти команду на роль текущей по таймингу
                //доинициализировать комманду
                //сделать команду текущей


                command = getNextCurrentCommand(truck.getId());
                if(command==null){

                }
                else{

                    Invoker invoker = new Invoker();
                    invoker.setDefaultProperties(new DefaultProperties());
                    invoker.setTruck(truck);
                    invoker.setCommand(command);
                    invoker.setTruckRepo(this.truckRepository);
                    invoker.setGoToCommandRepo(this.goToCommandRepository);
                    invoker.setRefuelCommandRepo(this.refuelCommandRepository);
                    invoker.setRepairCommandRepo(this.repairCommandRepository);
                    invoker.setLoadCommandRepo(this.loadCommandRepository);
                    invoker.setUnloadCommandRepo(this.unloadCommandRepository);
                    invoker.setFreightRepo(this.freightRepository);
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
        Optional<UnloadCommand> unloadCommand = unloadCommandRepository.findByIdTruckAndStatusCurrent(id,
                StatusCommand.CURRENT);
        if (!(goToCommand.isEmpty())) return goToCommand.get();
        if (!(refuelCommand.isEmpty())) return refuelCommand.get();
        if (!(repairCommand.isEmpty())) return repairCommand.get();
        if (!(loadCommand.isEmpty())) return loadCommand.get();
        if (!(unloadCommand.isEmpty())) return unloadCommand.get();
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
        for (UnloadCommand unloadCommand :
                unloadCommandRepository.findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, StatusCommand.FUTURE)) {
            if(!(checkTimeStart(unloadCommand.getTimeStart()))){
                return unloadCommand;
            }
        }
        return null;
    }


}

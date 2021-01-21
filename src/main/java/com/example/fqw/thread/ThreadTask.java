package com.example.fqw.thread;

import com.example.fqw.entity.GoToCommand;
import com.example.fqw.entity.Truck;
import com.example.fqw.logic.DefaultProperties;
import com.example.fqw.logic.ICommand;
import com.example.fqw.logic.Invoker;
import com.example.fqw.logic.StatusCommand;
import com.example.fqw.repository.GoToCommandRepository;
import com.example.fqw.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
//@Scope("prototype")
public class ThreadTask implements Runnable {
    @Autowired
    private GoToCommandRepository commandRepository;
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

                }

            }else {
                System.out.println("Truck: " + truck.getName() +" ### " + truck.getNumber() +
                        " ### " + truck.getId() + " no current command ");
                //найти команду на роль текущей по таймингу
                //доинициализировать комманду
                //сделать команду текущей

                ICommand iCommand = getNextCurrentCommand(truck.getId());
                if(iCommand==null){
                    System.out.println("Truck: " + truck.getName() +" ### " + truck.getNumber() +
                            " ### " + truck.getId() + " there is no suitable command for the current role");

                }
                else{
                    System.out.println("Truck: " + truck.getName() +" ### " + truck.getNumber() +
                            " ### " + truck.getId() + " catching command for the current role");

                    Invoker invoker = new Invoker();
                    invoker.setDefaultProperties(new DefaultProperties());
                    invoker.setTruck(truck);
                    invoker.setCommand(iCommand);
                    invoker.setTruckRepo(this.truckRepository);
                    invoker.setGoToCommandRepo(this.commandRepository);
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
        Optional<GoToCommand> goToCommand = commandRepository.findByIdTruckAndStatusCurrent(id,
                StatusCommand.CURRENT);
        if (!(goToCommand.isEmpty())) return goToCommand.get();
        return null;
    }

    public ICommand getNextCurrentCommand(long idTruck){
        for (GoToCommand goToCommand :
                commandRepository.findByIdTruckAndStatusFutureBySortedByTimeStart(idTruck, StatusCommand.FUTURE)) {
            if(!(checkTimeStart(goToCommand.getTimeStart()))){
//                goToCommand.setStatusCommand(StatusCommand.CURRENT);
                return goToCommand;
            }
        }
        return null;
    }


}

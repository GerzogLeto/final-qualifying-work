package com.example.fqw.thread;

import com.example.fqw.entity.Truck;
import com.example.fqw.logic.ICommand;
import com.example.fqw.repository.GoToCommandRepository;
import com.example.fqw.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@Scope("prototype")
public class ThreadTask implements Runnable {
    @Autowired
    private GoToCommandRepository commandRepository;
    @Autowired
    private TruckRepository truckRepository;
    @Override
    public void run() {
        for (Truck o : truckRepository.findAll() ) {
            for (ICommand o1 : commandRepository.findAllByIdTruck(o.getId()) ) {
                //if(o1==null)break;
                System.out.println(o1.getCommandType());
                System.out.println(o1.getTimeStart());
            }
        }
    }
}

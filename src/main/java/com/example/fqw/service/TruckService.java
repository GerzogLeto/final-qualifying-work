package com.example.fqw.service;

import com.example.fqw.entity.Freight;
import com.example.fqw.entity.Truck;
import com.example.fqw.exception.FreightException;
import com.example.fqw.exception.PositionException;
import com.example.fqw.exception.TruckException;
import com.example.fqw.logic.DefaultProperties;
import com.example.fqw.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TruckService {
    @Autowired
    private TruckRepository repository;

    public Truck add(Truck truck){
        if(repository.existsById(truck.getId()))
            throw new TruckException("Запись уже существует.");
        DefaultProperties defaultProperties = new DefaultProperties();
        truck.setMileageNextRepair(truck.getMileage() +
                Integer.parseInt(defaultProperties.getProperties().getProperty("mileage_between_repairs")));
        truck.setFuelTankCapacity(Integer.
                parseInt(defaultProperties.getProperties().getProperty("fuel_tank_capacity")));
        Truck saved = repository.save(truck);
        return saved;
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new TruckException("Запись не существует");
        repository.deleteById(id);
    }

    public Truck update(Truck truck){
        if(!repository.existsById(truck.getId())){
            throw new TruckException("Запись не существует");
        }
        return repository.save(truck);
    }

    public Iterable<Truck> getAll(){
        return repository.findAll();
    }

    public Optional<Truck> getById(long id) {
        Optional<Truck> optionalCourse = repository.findById(id);
        if (optionalCourse.isEmpty()) throw new TruckException("Запись не существует");
        return optionalCourse;
    }
}

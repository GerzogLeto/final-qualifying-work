package com.example.fqw.service;

import com.example.fqw.entity.Freight;
import com.example.fqw.entity.Truck;
import com.example.fqw.exception.FreightException;
import com.example.fqw.exception.PositionException;
import com.example.fqw.repository.FreightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FreightService {
    @Autowired
    private FreightRepository repository;

    public Freight add(Freight freight){
        if(repository.existsById(freight.getId()))
            throw new FreightException("Запись уже существует.");
        if(freight.getName().length() < 2){
            throw new FreightException("В названии товара меньше 2 символов");
        }
        if(freight.getWeight() <= 0){
            throw new FreightException("Вес груза меньше или равен 0");
        }
        if(freight.getTimeOfLoadingCargo().isBefore(LocalDateTime.now().plusHours(1))){
            throw new FreightException("Некорректное время начала загрузки");
        }
        if(freight.getTimeOfUnloadingCargo().isBefore(LocalDateTime.now().plusHours(1))){
            throw new FreightException("Некорректное время начала разгрузки");
        }
        if(freight.getTimeOfUnloadingCargo().isBefore(freight.getTimeOfLoadingCargo())){
            throw new FreightException("Время разгрузки раньше времени загрузки");
        }
        if(freight.getDurationOfLoadingCargo() <= 0){
            throw new FreightException("Некорректное время продолжительности загрузки");
        }
        if(freight.getDurationOfUnloadingCargo() <= 0){
            throw new FreightException("Некорректное время продолжительности разгрузки");
        }
        if(freight.getCost() <= 0){
            throw new FreightException("Стоимость доставки не может быть меньше или равно 0");
        }
        freight.setOpen(true);
        return repository.save(freight);
    }

    public void delete(long id){
        if (!repository.existsById(id)) throw new PositionException("Запись не существует");
        repository.deleteById(id);
    }

    public Freight update(Freight freight){
        if(!repository.existsById(freight.getId())){
            throw new FreightException("Запись не существует");
        }
        if(freight.getName().length() < 2){
            throw new FreightException("В названии товара меньше 2 символов");
        }
        if(freight.getWeight() <= 0){
            throw new FreightException("Вес груза меньше или равен 0");
        }
        if(freight.getTimeOfLoadingCargo().isBefore(LocalDateTime.now().plusHours(1))){
            throw new FreightException("Некорректное время начала загрузки");
        }
        if(freight.getTimeOfUnloadingCargo().isBefore(LocalDateTime.now().plusHours(1))){
            throw new FreightException("Некорректное время начала разгрузки");
        }
        if(freight.getTimeOfUnloadingCargo().isBefore(freight.getTimeOfLoadingCargo())){
            throw new FreightException("Время разгрузки раньше времени загрузки");
        }
        if(freight.getDurationOfLoadingCargo() <= 0){
            throw new FreightException("Некорректное время продолжительности загрузки");
        }
        if(freight.getDurationOfUnloadingCargo() <= 0){
            throw new FreightException("Некорректное время продолжительности разгрузки");
        }
        if(freight.getCost() <= 0){
            throw new FreightException("Стоимость доставки не может быть меньше или равно 0");
        }
        return repository.save(freight);
    }

    public Optional<Freight> getById(long id) {
        Optional<Freight> optionalFreight = repository.findById(id);
        if (optionalFreight.isEmpty()) throw new FreightException("Запись не существует");
        return optionalFreight;
    }

    public Iterable<Freight> getAll(){
        return repository.findAll();
    }

    public Iterable<Freight> findByLoadPlace(String start){
        return repository.findByLoadPlace(start);
    }
}

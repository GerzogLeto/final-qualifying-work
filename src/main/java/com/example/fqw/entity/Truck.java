package com.example.fqw.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class Truck extends Identity {
    @Getter
    @Setter
    @Column(nullable = false)
    private String name;
    @Getter
    @Setter
    @Column(nullable = false)
    private String number;
    @Getter
    @Setter
    @Column(nullable = false)
    private int capacity;
    @Getter
    @Setter
    @Column(nullable = false)
    private int mileage;
    @Getter
    @Setter
    private int mileageNextRepair;
    @Getter
    @Setter
    @Column(nullable = false)
    private int FuelTankCapacity;
    @Getter
    @Setter
    @Column(nullable = false)
    private int amountOfFuel;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Position position;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Freight freight;

}

package com.example.fqw.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
@Entity
public class Freight extends Identity {
    @Getter
    @Setter
    @Column(nullable = false)
    private String name;
    @Getter
    @Setter
    @Column(nullable = false)
    private int weight;
    @Getter
    @Setter
    @OneToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Position placeOfLoadingCargo;
    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime timeOfLoadingCargo;
    @Getter
    @Setter
    @OneToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Position placeOfUnloadingCargo;
    @Getter
    @Setter
    @Column(nullable = false)
    private long durationOfLoadingCargo;
    @Getter
    @Setter
    @Column(nullable = false)
    private long durationOfUnLoadingCargo;
    @Getter
    @Setter
    @Column(nullable = false)
    private int cost;

}

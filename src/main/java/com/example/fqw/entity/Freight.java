package com.example.fqw.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Position placeOfLoadingCargo;
    @Getter
    @Setter
    @Column(nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeOfLoadingCargo;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Position placeOfUnloadingCargo;
    @Getter
    @Setter
    @Column(nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeOfUnloadingCargo;
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

package com.example.fqw.entity;

import com.example.fqw.entity.Identity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Long.class
)
@Entity
public class Position extends Identity {
    @Getter
    @Setter
    @Column(nullable = false)
    private String start;
    @Getter
    @Setter
    @Column(nullable = false)
    private String finish;
    @Getter
    @Setter
    @Column(nullable = false)
    private int distFromStart;
    @Getter
    @Setter
    @Column(nullable = false)
    private int totalDist;
}

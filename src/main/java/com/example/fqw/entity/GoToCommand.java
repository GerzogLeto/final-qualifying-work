package com.example.fqw.entity;

import com.example.fqw.entity.Identity;
import com.example.fqw.entity.Position;
import com.example.fqw.logic.CommandTypes;
import com.example.fqw.logic.ICommand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class GoToCommand extends Identity implements ICommand {
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommandTypes commandType;
    @Setter
    @Column(nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeStart;
    @Getter
    @Setter
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeFinish;
    @Getter
    @Setter
    @Column(nullable = false)
    private long idTruck;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Position posStart;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Position posFinish;
    @Getter
    @Setter
    private int distance;
    @Getter
    @Setter
    private int duration;
    @Getter
    @Setter
    private boolean current;
    @Getter
    @Setter
    private boolean archive;
    @Getter
    @Setter
    private boolean future;

    @Override
    public CommandTypes getCommandType() {
        return commandType;
    }

    @Override
    public LocalDateTime getTimeStart() {
        return timeStart;
    }
}

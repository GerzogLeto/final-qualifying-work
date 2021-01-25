package com.example.fqw.entity;

import com.example.fqw.logic.CommandTypes;
import com.example.fqw.logic.ICommand;
import com.example.fqw.logic.StatusCommand;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
public class RefuelCommand extends Identity implements ICommand {
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommandTypes commandType;
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCommand statusCommand;
    @Setter
    @Column(nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeStart;
    @Setter
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeFinish;
    @Getter
    @Setter
    @Column(nullable = false)
    private long idTruck;


    @Override
    public CommandTypes getCommandType() {
        return this.commandType;
    }

    @Override
    public LocalDateTime getTimeStart() {
        return this.timeStart;
    }

    @Override
    public StatusCommand getStatusCommand() {
        return this.statusCommand;
    }

    @Override
    public LocalDateTime getTimeFinish() {
        return this.timeFinish;
    }
}

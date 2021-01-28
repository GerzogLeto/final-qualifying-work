package com.example.fqw.entity;

import com.example.fqw.entity.Identity;
import com.example.fqw.entity.Position;
import com.example.fqw.exception.GoToCommandException;
import com.example.fqw.logic.CommandTypes;
import com.example.fqw.logic.ICommand;
import com.example.fqw.logic.StatusCommand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCommand statusCommand;

    @Override
    public CommandTypes getCommandType() {
        return commandType;
    }

    @Override
    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    @Override
    public LocalDateTime getTimeFinish() {
        return timeFinish;
    }

    public void setDistance() {
        if(posStart.getStart().equals(posFinish.getStart())&&
                posStart.getFinish().equals(posFinish.getFinish())){
            this.distance = Math.abs(posFinish.getDistFromStart()-
                    posStart.getDistFromStart());
            return;
        }
        if(posStart.getStart().equals(posFinish.getFinish())&&
        posStart.getFinish().equals(posFinish.getStart())){
            String temp = posStart.getFinish();
            posStart.setFinish(posStart.getStart());
            posStart.setStart(temp);
            posStart.setDistFromStart(posStart.getTotalDist() -
                    posStart.getDistFromStart());
            setDistance();
            return;
        }
        if(posStart.getStart().equals(posFinish.getStart())&&
                !(posStart.getFinish().equals(posFinish.getFinish()))){
            this.distance = posStart.getDistFromStart() + posFinish.getDistFromStart();
            return;
        }

        if(posStart.getStart().equals(posFinish.getFinish())&&
                !(posStart.getFinish().equals(posFinish.getStart()))){
            this.distance = posStart.getDistFromStart() + posFinish.getTotalDist() - posFinish.getDistFromStart();
            return;
        }

        if(posStart.getFinish().equals(posFinish.getStart())&&
                !(posStart.getStart().equals(posFinish.getFinish()))){
            this.distance = posStart.getTotalDist() - posStart.getDistFromStart() + posFinish.getDistFromStart();
            return;
        }

        if(posStart.getFinish().equals(posFinish.getFinish())&&
                !(posStart.getStart().equals(posFinish.getStart()))){
            this.distance = posStart.getTotalDist() - posStart.getDistFromStart() +
                    posFinish.getTotalDist() - posFinish.getDistFromStart();
            return;
        }
        throw new GoToCommandException("Некорректный путь назначения в команде GOTO");
    }

    public void setUpDuration(int speed){
        this.duration =(int)((double)this.distance / speed * 60);
    }
}

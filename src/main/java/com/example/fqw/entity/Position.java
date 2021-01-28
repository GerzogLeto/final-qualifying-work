package com.example.fqw.entity;

import com.example.fqw.entity.Identity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return (distFromStart == position.distFromStart &&
                totalDist == position.totalDist &&
                start.equals(position.start) &&
                finish.equals(position.finish))||
                (distFromStart == (position.totalDist - position.distFromStart) &&
                        totalDist == position.totalDist &&
                        start.equals(position.finish) &&
                        finish.equals(position.start));
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalDist);
    }
}

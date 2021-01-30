package com.example.fqw.entity;

import com.example.fqw.entity.Identity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;
/**This class abstracts the positioning of objects in relation to positioning control points.
 * Large cities are taken as positioning control points. Positioning is based on the concept of a
 * radius vector. A large city on the map is conventionally taken as the beginning of the radius vector.
 * The end of the radius vector is another big city. The length of the radius vector is the distance between
 * cities. The radius vector of the object coincides in the direction with the radius vector between cities.
 * The length of the radius vector of the object is the distance between the start city and the target object.
 * */
@Entity
public class Position extends Identity {
    /**This field is the name of the city, taken as a starting point.*/
    @Getter
    @Setter
    @Column(nullable = false)
    private String start;
    /**This field is the name of the city taken as the finish point.*/
    @Getter
    @Setter
    @Column(nullable = false)
    private String finish;
    /**This field is the distance from the starting point to the target object.*/
    @Getter
    @Setter
    @Column(nullable = false)
    private int distFromStart;
    /**This field is the distance from the start point to the finish point.*/
    @Getter
    @Setter
    @Column(nullable = false)
    private int totalDist;
    /**Compares this position to the specified object. The result is true if and only if the argument is not
     * null and is a Position object that represents the same fields this object.
     * @param o -  The object to compare this {@code Position} against.
     * @return true if:
     * <ul>
     *     <li>this position and argument point to the same object.</li>
     *     <li>this position and argument in the fields have the same value.</li>
     *     <li>this position in the field {@code start} is equal to the field {@code finish} of the passed object,
     *     the field {@code finish} is equal to the field {@code start} of the passed object,
     *     the field {@code totalDist} is equal to the field {@code totalDist} of the passed object,
     *     the field {@code distFromStart} is equal to the expression {@code totalDist - distFromStart} of the
     *     passed object</li>
     * </ul>
     * Otherwise returns false.
     * */
    @Override
    public boolean equals(Object o) {
        o = Hibernate.unproxy(o);
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        if(getId()== position.getId()) return true;
        return (distFromStart == position.distFromStart &&
                totalDist == position.totalDist &&
                start.equals(position.start) &&
                finish.equals(position.finish))||
                (distFromStart == (position.totalDist - position.distFromStart) &&
                        totalDist == position.totalDist &&
                        start.equals(position.finish) &&
                        finish.equals(position.start));
    }
    /**This method calculates the hash code of the object.
     * @return - hash code of the object.
     * */
    @Override
    public int hashCode() {
        return Objects.hash(totalDist);
    }
}

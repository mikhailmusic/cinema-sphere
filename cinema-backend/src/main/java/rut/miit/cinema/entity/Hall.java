package rut.miit.cinema.entity;

import jakarta.persistence.*;
import rut.miit.cinema.exception.ValidationException;

@Entity
@Table(name = "halls")
public class Hall extends BaseEntity {
    private String name;
    private int seatCount;

    public Hall(String name, int seatCount) {
        setName(name);
        setSeatCount(seatCount);
    }
    protected Hall() {
    }

    @Column(name = "name", nullable = false, unique = true, length = 100)
    public String getName() {
        return name;
    }

    @Column(name = "seat_count", nullable = false)
    public int getSeatCount() {
        return seatCount;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Hall name cannot be null or blank.");
        }
        if (name.length() > 100) {
            throw new ValidationException("Hall name cannot exceed 100 characters.");
        }
        this.name = name;
    }

    public void setSeatCount(int seatCount) {
        if (seatCount <= 0) {
            throw new ValidationException("Seat count must be greater than zero.");
        }
        this.seatCount = seatCount;
    }
}

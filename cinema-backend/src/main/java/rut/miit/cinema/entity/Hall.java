package rut.miit.cinema.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "halls")
public class Hall extends BaseEntity {
    private String name;
    private int seatCount;

    public Hall(String name, int seatCount) {
        this.name = name;
        this.seatCount = seatCount;
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
        this.name = name;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
}

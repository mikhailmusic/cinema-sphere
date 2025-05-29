package rut.miit.cinema.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre extends BaseEntity {
    private String name;

    public Genre(String name) {
        this.name = name;
    }
    protected Genre() {
    }

    @Column(name = "name", nullable = false, unique = true, length = 50)
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

}

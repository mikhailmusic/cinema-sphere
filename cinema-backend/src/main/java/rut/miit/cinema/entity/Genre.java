package rut.miit.cinema.entity;

import jakarta.persistence.*;
import rut.miit.cinema.exception.ValidationException;

@Entity
@Table(name = "genres")
public class Genre extends BaseEntity {
    private String name;

    public Genre(String name) {
        setName(name);
    }
    protected Genre() {
    }

    @Column(name = "name", nullable = false, unique = true, length = 50)
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Genre name cannot be null or blank.");
        }
        if (name.length() > 50) {
            throw new ValidationException("Genre name cannot exceed 50 characters.");
        }
        this.name = name;
    }

}

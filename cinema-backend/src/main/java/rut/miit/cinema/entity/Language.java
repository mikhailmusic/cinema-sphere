package rut.miit.cinema.entity;

import jakarta.persistence.*;
import rut.miit.cinema.exception.ValidationException;

@Entity
@Table(name = "languages")
public class Language extends BaseEntity {
    private String name;
    private String code;

    public Language(String name, String code) {
        setName(name);
        setCode(code);
    }

    protected Language() {
    }

    @Column(name = "name", nullable = false, unique = true, length = 50)
    public String getName() {
        return name;
    }

    @Column(name = "code", nullable = false, unique = true, length = 10)
    public String getCode() {
        return code;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Language name cannot be null or blank.");
        }
        if (name.length() > 50) {
            throw new ValidationException("Language name cannot exceed 50 characters.");
        }
        this.name = name;
    }

    public void setCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Language code cannot be null or blank.");
        }
        if (code.length() > 10) {
            throw new ValidationException("Language code cannot exceed 10 characters.");
        }
        this.code = code;
    }
}

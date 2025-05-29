package rut.miit.cinema.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "languages")
public class Language extends BaseEntity {
    private String name;
    private String code;

    public Language(String name, String code) {
        this.name = name;
        this.code = code;
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
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

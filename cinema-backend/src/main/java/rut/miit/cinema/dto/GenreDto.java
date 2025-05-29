package rut.miit.cinema.dto;

public class GenreDto {
    private Integer id;
    private String name;

    public GenreDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

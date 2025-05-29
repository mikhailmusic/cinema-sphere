package rut.miit.cinema.dto;

public class LanguageDto {
    private Integer id;
    private String name;
    private String code;

    public LanguageDto(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}

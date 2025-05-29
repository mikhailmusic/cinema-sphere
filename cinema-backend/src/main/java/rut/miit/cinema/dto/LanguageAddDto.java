package rut.miit.cinema.dto;

public class LanguageAddDto {
    private String name;
    private String code;

    public LanguageAddDto(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}

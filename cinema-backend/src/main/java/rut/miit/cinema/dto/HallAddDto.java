package rut.miit.cinema.dto;

public class HallAddDto {
    private String name;
    private Integer seatCount;

    public HallAddDto(String name, int seatCount) {
        this.name = name;
        this.seatCount = seatCount;
    }

    public String getName() {
        return name;
    }

    public int getSeatCount() {
        return seatCount;
    }
}

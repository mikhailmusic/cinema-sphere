package rut.miit.cinema.dto;

public class HallDto {
    private Integer id;
    private String name;
    private Integer seatCount;

    public HallDto(Integer id, String name, int seatCount) {
        this.id = id;
        this.name = name;
        this.seatCount = seatCount;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSeatCount() {
        return seatCount;
    }
}

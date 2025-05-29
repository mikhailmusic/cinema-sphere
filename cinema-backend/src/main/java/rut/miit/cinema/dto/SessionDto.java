package rut.miit.cinema.dto;

import java.time.LocalDateTime;

public class SessionDto {
    private Integer id;
    private LocalDateTime startTime;
    private String status;
    private String hallName;
    private int seatCount;

    public SessionDto(Integer id, LocalDateTime startTime, String status, String hallName, int seatCount) {
        this.id = id;
        this.startTime = startTime;
        this.status = status;
        this.hallName = hallName;
        this.seatCount = seatCount;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getStatus() {
        return status;
    }

    public String getHallName() {
        return hallName;
    }

    public int getSeatCount() {
        return seatCount;
    }
}

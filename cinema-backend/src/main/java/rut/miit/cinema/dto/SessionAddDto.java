package rut.miit.cinema.dto;

import java.time.LocalDateTime;

public class SessionAddDto {
    private Integer hallId;
    private Integer movieId;
    private LocalDateTime startTime;
    private String status;

    public SessionAddDto(Integer hallId, Integer movieId, LocalDateTime startTime, String status) {
        this.hallId = hallId;
        this.movieId = movieId;
        this.startTime = startTime;
        this.status = status;
    }

    public Integer getHallId() {
        return hallId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getStatus() {
        return status;
    }
}

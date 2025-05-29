package rut.miit.cinema.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
public class Session extends BaseEntity {
    private Movie movie;
    private LocalDateTime startTime;
    private SessionStatus status;
    private Hall hall;

    public Session(Movie movie, LocalDateTime startTime, Hall hall) {
        this.movie = movie;
        this.startTime = startTime;
        this.status = SessionStatus.ACTIVE;
        this.hall = hall;
    }
    protected Session() {
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    public Movie getMovie() {
        return movie;
    }

    @Column(name = "start_time", nullable = false)
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public SessionStatus getStatus() {
        return status;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "hall_id", nullable = false)
    public Hall getHall() {
        return hall;
    }

    protected void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}

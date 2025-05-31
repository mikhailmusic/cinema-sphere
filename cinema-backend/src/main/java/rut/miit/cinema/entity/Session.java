package rut.miit.cinema.entity;

import jakarta.persistence.*;
import rut.miit.cinema.exception.ValidationException;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
public class Session extends BaseEntity {
    private Movie movie;
    private LocalDateTime startTime;
    private SessionStatus status;
    private Hall hall;

    public Session(Movie movie, LocalDateTime startTime, Hall hall, SessionStatus status) {
        setMovie(movie);
        setStartTime(startTime);
        setHall(hall);
        this.status = status;
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
        if (movie == null) {
            throw new ValidationException("Movie cannot be null.");
        }
        this.movie = movie;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (startTime == null) {
            throw new ValidationException("Start time cannot be null.");
        }
        this.startTime = startTime;
    }

    public void setStatus(SessionStatus status) {
        if (status == null) {
            throw new ValidationException("Session status cannot be null.");
        }
        this.status = status;
    }

    public void setHall(Hall hall) {
        if (hall == null) {
            throw new ValidationException("Hall cannot be null.");
        }
        this.hall = hall;
    }
}

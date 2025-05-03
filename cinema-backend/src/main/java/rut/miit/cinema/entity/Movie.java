package rut.miit.cinema.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDate;

@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {
    private String title;
    private int duration;
    private LocalDate releaseDate;
    private String director;
    private String imageUrl;
    private String language;
    private String country;
    private String  description;
    private String ageRating;
    private Status status;
    private LocalDate startTime;
    private LocalDate endTime;

    public Movie(String title, int duration, LocalDate releaseDate, String director, String imageUrl, String language, String country, String description, String ageRating, Status status, LocalDate startTime, LocalDate endTime) {
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.director = director;
        this.imageUrl = imageUrl;
        this.language = language;
        this.country = country;
        this.description = description;
        this.ageRating = ageRating;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected Movie(){}


    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "duration", nullable = false)
    public int getDuration() {
        return duration;
    }

    @Column(name = "release_date", nullable = false)
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @Column(name = "director", length = 100)
    public String getDirector() {
        return director;
    }

    @Column(name = "image_url", nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    @Column(name = "language", nullable = false, length = 50)
    public String getLanguage() {
        return language;
    }

    @Column(name = "country", length = 100)
    public String getCountry() {
        return country;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    @Column(name = "age_rating", nullable = false, length = 7)
    public String getAgeRating() {
        return ageRating;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public Status getStatus() {
        return status;
    }

    @Column(name = "start_time", nullable = false)
    public LocalDate getStartTime() {
        return startTime;
    }

    @Column(name = "end_time", nullable = false)
    public LocalDate getEndTime() {
        return endTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }
}
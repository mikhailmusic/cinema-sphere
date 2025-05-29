package rut.miit.cinema.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {
    private String title;
    private int duration;
    private int releaseYear;
    private String director;
    private String imageKey;
    private String description;
    private int ageRating;
    private Language language;
    private Genre genre;
    private MovieStatus movieStatus;
    private List<Session> sessionList;

    public Movie(String title, int duration, int releaseYear, String director, String imageKey, String description, int ageRating, Language language, Genre genre, MovieStatus movieStatus) {
        this.title = title;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.director = director;
        this.imageKey = imageKey;
        this.description = description;
        this.ageRating = ageRating;
        this.language = language;
        this.genre = genre;
        this.movieStatus = movieStatus;
    }

    protected Movie() {
    }


    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "duration", nullable = false)
    public int getDuration() {
        return duration;
    }

    @Column(name = "release_year", nullable = false)
    public int getReleaseYear() {
        return releaseYear;
    }

    @Column(name = "director", length = 100)
    public String getDirector() {
        return director;
    }

    @Column(name = "image_key", nullable = false)
    public String getImageKey() {
        return imageKey;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    @Column(name = "age_rating", nullable = false)
    public int getAgeRating() {
        return ageRating;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    public Language getLanguage() {
        return language;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "genre_id", nullable = false)
    public Genre getGenre() {
        return genre;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "movie_status", nullable = false)
    public MovieStatus getMovieStatus() {
        return movieStatus;
    }


    @OneToMany(mappedBy = "movie")
    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        this.movieStatus = movieStatus;
    }

    protected void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }
}
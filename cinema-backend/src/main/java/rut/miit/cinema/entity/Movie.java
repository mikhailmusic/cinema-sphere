package rut.miit.cinema.entity;

import jakarta.persistence.*;
import rut.miit.cinema.exception.ValidationException;

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
        setTitle(title);
        setDuration(duration);
        setReleaseYear(releaseYear);
        setDirector(director);
        setImageKey(imageKey);
        setDescription(description);
        setAgeRating(ageRating);
        setLanguage(language);
        setGenre(genre);
        setMovieStatus(movieStatus);
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
        if (title == null || title.isBlank()) {
            throw new ValidationException("Movie title cannot be null or blank.");
        }
        this.title = title;
    }

    public void setDuration(int duration) {
        if (duration <= 0) {
            throw new ValidationException("Duration must be greater than 0.");
        }
        this.duration = duration;
    }

    public void setReleaseYear(int releaseYear) {
        if (releaseYear < 1888) {
            throw new ValidationException("Release year must not be earlier than 1888.");
        }
        this.releaseYear = releaseYear;
    }

    public void setDirector(String director) {
        if (director != null && director.length() > 100) {
            throw new ValidationException("Director name cannot exceed 100 characters.");
        }
        this.director = director;
    }

    public void setImageKey(String imageKey) {
        if (imageKey == null || imageKey.isBlank()) {
            throw new ValidationException("Image key cannot be null or blank.");
        }
        this.imageKey = imageKey;
    }

    public void setLanguage(Language language) {
        if (language == null) {
            throw new ValidationException("Language cannot be null.");
        }
        this.language = language;
    }

    public void setGenre(Genre genre) {
        if (genre == null) {
            throw new ValidationException("Genre cannot be null.");
        }
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAgeRating(int ageRating) {
        if (ageRating < 0 || ageRating > 100) {
            throw new ValidationException("Age rating must be between 0 and 100.");
        }
        this.ageRating = ageRating;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        if (movieStatus == null) {
            throw new ValidationException("Movie status cannot be null.");
        }
        this.movieStatus = movieStatus;
    }

    protected void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }
}
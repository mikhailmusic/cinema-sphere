package rut.miit.cinema.dto;

import java.util.List;

public class MovieDto {
    private Integer id;
    private String title;
    private int duration;
    private int releaseYear;
    private String director;
    private String image;
    private String description;
    private int ageRating;
    private String language;
    private String genre;
    private String movieStatus;
    private List<SessionDto> sessionList;

    public MovieDto(Integer id, String title, int duration, int releaseYear, String director, String image, String description, int ageRating, String language, String genre, String movieStatus, List<SessionDto> sessionList) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.director = director;
        this.image = image;
        this.description = description;
        this.ageRating = ageRating;
        this.language = language;
        this.genre = genre;
        this.movieStatus = movieStatus;
        this.sessionList = sessionList;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public String getLanguage() {
        return language;
    }

    public String getGenre() {
        return genre;
    }

    public String getMovieStatus() {
        return movieStatus;
    }

    public List<SessionDto> getSessionList() {
        return sessionList;
    }
}

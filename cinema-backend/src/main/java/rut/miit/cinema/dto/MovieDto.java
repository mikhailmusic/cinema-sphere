package rut.miit.cinema.dto;


import java.time.LocalDate;

public class MovieDto {
    private Integer id;
    private String title;
    private int duration;
    private LocalDate releaseDate;
    private String director;
    private String imageUrl;
    private String language;
    private String country;
    private String description;
    private String ageRating;
    private String status;
    private LocalDate startTime;
    private LocalDate endTime;

    public MovieDto(Integer id, String title, int duration, LocalDate releaseDate, String director, String imageUrl, String language, String country, String description, String ageRating, String status, LocalDate startTime, LocalDate endTime) {
        this.id = id;
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

    protected MovieDto() {
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }
}

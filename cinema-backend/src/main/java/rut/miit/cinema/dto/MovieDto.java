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

    public void setId(Integer id) {
        this.id = id;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }
}

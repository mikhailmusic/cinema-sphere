package rut.miit.cinema.dto;

public class MovieAddDto {
    private Integer languageId;
    private Integer genreId;

    private String title;
    private Integer duration;
    private Integer releaseYear;
    private String director;
    private String image;
    private String description;
    private Integer ageRating;
    private String movieStatus;

    public MovieAddDto(Integer languageId, Integer genreId, String title, Integer duration, Integer releaseYear, String director, String image, String description, Integer ageRating, String movieStatus) {
        this.languageId = languageId;
        this.genreId = genreId;
        this.title = title;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.director = director;
        this.image = image;
        this.description = description;
        this.ageRating = ageRating;
        this.movieStatus = movieStatus;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getReleaseYear() {
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

    public Integer getAgeRating() {
        return ageRating;
    }

    public String getMovieStatus() {
        return movieStatus;
    }
}

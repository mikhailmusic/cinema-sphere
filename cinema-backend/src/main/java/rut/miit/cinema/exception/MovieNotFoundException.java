package rut.miit.cinema.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
        super(message);
    }
    public MovieNotFoundException(Integer id) {
        super("Movie with id " + id + " not found");
    }
}

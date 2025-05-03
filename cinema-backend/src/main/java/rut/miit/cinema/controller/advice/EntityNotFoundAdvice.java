package rut.miit.cinema.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rut.miit.cinema.exception.MovieNotFoundException;

@RestControllerAdvice
public class EntityNotFoundAdvice {
    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundHandler(MovieNotFoundException ex) {
        return ex.getMessage();
    }
}

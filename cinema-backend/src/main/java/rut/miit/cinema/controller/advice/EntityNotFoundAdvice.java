package rut.miit.cinema.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rut.miit.cinema.exception.*;

@RestControllerAdvice
public class EntityNotFoundAdvice {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleMovieNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}

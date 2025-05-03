package rut.miit.cinema.service;

import rut.miit.cinema.dto.MovieAddDto;
import rut.miit.cinema.dto.MovieDto;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {
    MovieDto addNewMovie(MovieAddDto dto);
    void updateMovieInfo(Integer id, MovieAddDto dto);
    MovieDto getMovieDetails(Integer id);
    List<MovieDto> findAllMovies();
    List<MovieDto> weeklyMoviesByStatus(String status);
    List<MovieDto> searchMoviesByTitle(String keyword);
    List<MovieDto> moviesInDateRange(LocalDate from, LocalDate to, String status);
}

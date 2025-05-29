package rut.miit.cinema.service;

import rut.miit.cinema.dto.MovieAddDto;
import rut.miit.cinema.dto.MovieDto;

import java.util.List;

public interface MovieService {
    void addNewMovie(MovieAddDto dto);
    void updateMovieInfo(Integer id, MovieAddDto dto);
    void logicRemoveMovieInfo(Integer id);
    MovieDto getMovieDetails(Integer id);
    List<MovieDto> findAllMovies();
    List<MovieDto> weeklyMoviesByStatus(String status);
    List<MovieDto> searchMoviesByTitle(String keyword);
}

package rut.miit.cinema.service;

import rut.miit.cinema.dto.ImageDto;
import rut.miit.cinema.dto.MovieAddDto;
import rut.miit.cinema.dto.MovieAddNoImageDto;
import rut.miit.cinema.dto.MovieDto;

import java.util.List;

public interface MovieService {
    MovieDto addNewMovie(MovieAddDto dto);
    MovieDto updateMovieInfo(Integer id, MovieAddDto dto);
    void logicRemoveMovieInfo(Integer id);
    MovieDto getMovieDetails(Integer id);
    List<MovieDto> findAllMovies();
    List<MovieDto> weeklyMoviesByStatus(String status);
    List<MovieDto> searchMoviesByTitle(String keyword);

    MovieDto addNewMovie(MovieAddNoImageDto movieDto, ImageDto imageDto);
    MovieDto updateMovieInfo(Integer id, MovieAddNoImageDto movieDto, ImageDto imageDto);
}

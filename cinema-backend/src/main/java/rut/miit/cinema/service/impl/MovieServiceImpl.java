package rut.miit.cinema.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.cinema.dto.MovieAddDto;
import rut.miit.cinema.dto.MovieDto;
import rut.miit.cinema.entity.Movie;
import rut.miit.cinema.entity.Status;
import rut.miit.cinema.exception.MovieNotFoundException;
import rut.miit.cinema.repository.MovieRepository;
import rut.miit.cinema.service.MovieService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MovieDto> weeklyMoviesByStatus(String status) {
        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.plusDays(7);
        List<Movie> movies = movieRepository.findMoviesBetweenDates(today, endOfWeek, Set.of(Status.valueOf(status)));
        return movies.stream().map(movie -> modelMapper.map(movie, MovieDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> searchMoviesByTitle(String keyword) {
        List<Movie> availableMovies = movieRepository.findByTitleContaining(keyword).stream()
                .filter(movie -> movie.getStatus().equals(Status.ACTIVE)).toList();
        return availableMovies.stream().map(movie -> modelMapper.map(movie, MovieDto.class)).collect(Collectors.toList());
    }

    @Override
    public MovieDto addNewMovie(MovieAddDto addDto) {
        Movie movie = modelMapper.map(addDto, Movie.class);
        movieRepository.save(movie);
        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public void updateMovieInfo(Integer id, MovieAddDto dto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        modelMapper.map(dto, movie);
        movieRepository.save(movie);
    }

    @Override
    public MovieDto getMovieDetails(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public List<MovieDto> findAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> modelMapper.map(movie, MovieDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> moviesInDateRange(LocalDate from, LocalDate to, String status) {
        List<Movie> movies = movieRepository.findMoviesBetweenDates(from, to, Set.of(Status.valueOf(status)));
        return movies.stream().map(movie -> modelMapper.map(movie, MovieDto.class)).collect(Collectors.toList());
    }
}

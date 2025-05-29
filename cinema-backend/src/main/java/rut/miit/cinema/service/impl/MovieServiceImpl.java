package rut.miit.cinema.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.cinema.dto.MovieAddDto;
import rut.miit.cinema.dto.MovieDto;
import rut.miit.cinema.dto.SessionDto;
import rut.miit.cinema.entity.*;
import rut.miit.cinema.exception.NotFoundException;
import rut.miit.cinema.repository.GenreRepository;
import rut.miit.cinema.repository.LanguageRepository;
import rut.miit.cinema.repository.MovieRepository;
import rut.miit.cinema.repository.SessionRepository;
import rut.miit.cinema.service.MovieService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
    private GenreRepository genreRepository;
    private LanguageRepository languageRepository;
    private SessionRepository sessionRepository;

    @Override
    public List<MovieDto> weeklyMoviesByStatus(String status) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime endOfWeek = today.plusDays(7);

        List<Session> sessions = sessionRepository.findByStartTimeBetweenAndStatus(
                today, endOfWeek, SessionStatus.valueOf(status)
        );

        List<Movie> movies = sessions.stream().map(Session::getMovie).distinct().toList();
        return movies.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> searchMoviesByTitle(String keyword) {
        List<Movie> availableMovies = movieRepository.findByTitleContaining(keyword).stream()
                .filter(movie -> movie.getMovieStatus().equals(MovieStatus.ACTIVE)).toList();
        return availableMovies.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void addNewMovie(MovieAddDto dto) {
        Language language = languageRepository.findById(dto.getLanguageId())
                .orElseThrow(() -> new NotFoundException(Language.class, dto.getLanguageId()));
        Genre genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new NotFoundException(Genre.class, dto.getGenreId()));

        Movie movie = new Movie(dto.getTitle(), dto.getDuration(), dto.getReleaseYear(), dto.getDirector(),
                dto.getImage(), dto.getDescription(), dto.getAgeRating(), language, genre,
                MovieStatus.valueOf(dto.getMovieStatus())
        );
        movieRepository.save(movie);
    }

    @Override
    public void updateMovieInfo(Integer id, MovieAddDto dto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException(Movie.class, id));
        Language language = languageRepository.findById(dto.getLanguageId()).orElseThrow(() -> new NotFoundException(Language.class, dto.getLanguageId()));
        Genre genre = genreRepository.findById(dto.getGenreId()).orElseThrow(() -> new NotFoundException(Genre.class, dto.getGenreId()));

        movie.setTitle(dto.getTitle());
        movie.setDuration(dto.getDuration());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDirector(dto.getDirector());
        movie.setLanguage(language);
        movie.setGenre(genre);
        movie.setDescription(dto.getDescription());
        movie.setAgeRating(dto.getAgeRating());
        movie.setMovieStatus(MovieStatus.valueOf(dto.getMovieStatus()));

        if (dto.getImage() != null && !dto.getImage().isBlank()) {
            movie.setImageKey(dto.getImage());
        }

        movieRepository.save(movie);
    }

    @Override
    public MovieDto getMovieDetails(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException(Movie.class, id));
        return convertToDto(movie);
    }

    @Override
    public List<MovieDto> findAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private MovieDto convertToDto(Movie movie) {
        List<SessionDto> sessionDtos = movie.getSessionList() != null
                ? movie.getSessionList().stream()
                .map(s -> new SessionDto(s.getId(), s.getStartTime(), s.getStatus().name(), s.getHall().getName(), s.getHall().getSeatCount()))
                .collect(Collectors.toList())
                : List.of();

        return new MovieDto(movie.getId(), movie.getTitle(), movie.getDuration(), movie.getReleaseYear(),
                movie.getDirector(), movie.getImageKey(), movie.getDescription(), movie.getAgeRating(),
                movie.getLanguage().getName(), movie.getGenre().getName(),
                movie.getMovieStatus().name(), sessionDtos
        );
    }

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    @Autowired
    public void setLanguageRepository(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }
    @Autowired
    public void setSessionRepository(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
}

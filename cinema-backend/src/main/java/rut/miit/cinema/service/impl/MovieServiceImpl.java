package rut.miit.cinema.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.cinema.dto.*;
import rut.miit.cinema.entity.*;
import rut.miit.cinema.exception.NotFoundException;
import rut.miit.cinema.repository.GenreRepository;
import rut.miit.cinema.repository.LanguageRepository;
import rut.miit.cinema.repository.MovieRepository;
import rut.miit.cinema.repository.SessionRepository;
import rut.miit.cinema.service.FileService;
import rut.miit.cinema.service.MovieService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
    private GenreRepository genreRepository;
    private LanguageRepository languageRepository;
    private SessionRepository sessionRepository;
    private FileService fileService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository, LanguageRepository languageRepository, SessionRepository sessionRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.languageRepository = languageRepository;
        this.sessionRepository = sessionRepository;
        this.fileService = fileService;
    }

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
    public MovieDto addNewMovie(MovieAddDto dto) {
        Movie movie = new Movie(dto.getTitle(), dto.getDuration(), dto.getReleaseYear(), dto.getDirector(),
                dto.getImage(), dto.getDescription(), dto.getAgeRating(), getLanguage(dto.getLanguageId()),
                getGenre(dto.getGenreId()), MovieStatus.valueOf(dto.getMovieStatus())
        );
        movieRepository.save(movie);
        return convertToDto(movie);
    }

    @Override
    public MovieDto addNewMovie(MovieAddNoImageDto dto, ImageDto imageDto) {
        String imageKey = fileService.addImage(imageDto);
        Movie movie = new Movie(dto.getTitle(), dto.getDuration(), dto.getReleaseYear(), dto.getDirector(),
                imageKey, dto.getDescription(), dto.getAgeRating(), getLanguage(dto.getLanguageId()),
                getGenre(dto.getGenreId()), MovieStatus.valueOf(dto.getMovieStatus())
        );
        movieRepository.save(movie);
        return convertToDto(movie);
    }

    @Override
    public MovieDto updateMovieInfo(Integer id, MovieAddDto dto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException(Movie.class, id));

        movie.setTitle(dto.getTitle());
        movie.setDuration(dto.getDuration());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDirector(dto.getDirector());
        movie.setLanguage(getLanguage(dto.getLanguageId()));
        movie.setGenre(getGenre(dto.getGenreId()));
        movie.setDescription(dto.getDescription());
        movie.setAgeRating(dto.getAgeRating());
        movie.setMovieStatus(MovieStatus.valueOf(dto.getMovieStatus()));

        if (dto.getImage() != null && !dto.getImage().isBlank()) {
            movie.setImageKey(dto.getImage());
        }

        movieRepository.save(movie);
        return convertToDto(movie);

    }

    @Override
    public MovieDto updateMovieInfo(Integer id, MovieAddNoImageDto dto, ImageDto imageDto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException(Movie.class, id));

        movie.setTitle(dto.getTitle());
        movie.setDuration(dto.getDuration());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDirector(dto.getDirector());
        movie.setLanguage(getLanguage(dto.getLanguageId()));
        movie.setGenre(getGenre(dto.getGenreId()));
        movie.setDescription(dto.getDescription());
        movie.setAgeRating(dto.getAgeRating());
        movie.setMovieStatus(MovieStatus.valueOf(dto.getMovieStatus()));

        if (imageDto != null && imageDto.getData() != null && imageDto.getData().length > 0) {
            String imageKey = fileService.addImage(imageDto);
            movie.setImageKey(imageKey);
        }
        movieRepository.save(movie);
        return convertToDto(movie);
    }

    @Override
    public void logicRemoveMovieInfo(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException(Movie.class, id));
        movie.setMovieStatus(MovieStatus.DELETED);
        movieRepository.save(movie);
    }

    @Override
    public MovieDto getMovieDetails(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException(Movie.class, id));
        return convertToDto(movie);
    }

    @Override
    public List<MovieDto> findAllMovies() {
        List<Movie> movies = movieRepository.findByStatuses(Set.of(MovieStatus.PLANNED, MovieStatus.ACTIVE, MovieStatus.ARCHIVED));
        return movies.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private MovieDto convertToDto(Movie movie) {
        List<SessionDto> sessionDtos = movie.getSessionList() != null
                ? movie.getSessionList().stream()
                .filter(s -> s.getStatus() != SessionStatus.DELETED)
                .map(s -> new SessionDto(s.getId(), s.getStartTime(), s.getStatus().name(), s.getHall().getName(), s.getHall().getSeatCount()))
                .collect(Collectors.toList())
                : List.of();

        return new MovieDto(movie.getId(), movie.getTitle(), movie.getDuration(), movie.getReleaseYear(),
                movie.getDirector(), movie.getImageKey(), movie.getDescription(), movie.getAgeRating(),
                movie.getLanguage().getName(), movie.getGenre().getName(),
                movie.getMovieStatus().name(), sessionDtos
        );
    }

    private Language getLanguage(Integer id) {
        return languageRepository.findById(id).orElseThrow(() -> new NotFoundException(Language.class, id));
    }
    private Genre getGenre(Integer id) {
        return genreRepository.findById(id).orElseThrow(() -> new NotFoundException(Genre.class, id));
    }
}

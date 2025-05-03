package rut.miit.cinema;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rut.miit.cinema.dto.MovieAddDto;
import rut.miit.cinema.service.MovieService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class MovieDataInitializer implements CommandLineRunner {
    private final MovieService movieService;

    public MovieDataInitializer(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void run(String... args) {
        if (movieService.findAllMovies().isEmpty()){

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("init/initial_movies.json")) {
                if (inputStream == null) {
                    throw new IllegalStateException("Файл init/initial_movies.json не найден");
                }
                List<MovieAddDto> initialMovies = objectMapper.readValue(inputStream, new TypeReference<List<MovieAddDto>>() {});
                initialMovies.forEach(movieService::addNewMovie);
                System.out.println("Начальные фильмы успешно добавлены");
            } catch (IOException e) {
                System.err.println("Ошибка при чтении или парсинге init/initial_movies.json: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Неожиданная ошибка при инициализации данных: " + e.getMessage());
            }

        }


    }
}


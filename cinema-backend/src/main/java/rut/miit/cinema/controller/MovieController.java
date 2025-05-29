package rut.miit.cinema.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rut.miit.cinema.dto.ImageDto;
import rut.miit.cinema.dto.MovieAddDto;
import rut.miit.cinema.dto.MovieAddNoImageDto;
import rut.miit.cinema.dto.MovieDto;
import rut.miit.cinema.service.MovieService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private static final Logger LOG = LogManager.getLogger(MovieController.class);
    private MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/no-image")
    public MovieDto addMovie(@RequestBody MovieAddDto dto) {
        return movieService.addNewMovie(dto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieDto> addMovie(@RequestPart("movie") MovieAddNoImageDto dto, @RequestPart("image") MultipartFile file) {
        try {
            ImageDto imageDto = new ImageDto(file.getBytes(), file.getContentType(), file.getOriginalFilename());
            MovieDto movie = movieService.addNewMovie(dto, imageDto);
            return ResponseEntity.ok(movie);
        } catch (IOException e) {
           LOG.warn("Ошибка при чтении файла: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/no-image")
    public MovieDto updateMovie(@PathVariable Integer id, @RequestBody MovieAddDto movieDto) {
        return movieService.updateMovieInfo(id, movieDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Integer id, @RequestPart("movie") MovieAddNoImageDto dto, @RequestPart(value = "image", required = false) MultipartFile file) {
        try {
            ImageDto imageDto = null;
            if (file != null && !file.isEmpty()) {
                imageDto = new ImageDto(file.getBytes(), file.getContentType(), file.getOriginalFilename());
            }
            MovieDto movie = movieService.updateMovieInfo(id, dto, imageDto);;
            return ResponseEntity.ok(movie);
        } catch (IOException e) {
            LOG.warn("Ошибка при чтении файла: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Integer id) {
        movieService.logicRemoveMovieInfo(id);
    }

    @GetMapping("/{id}")
    public MovieDto movieById(@PathVariable Integer id) {
        return movieService.getMovieDetails(id);
    }

    @GetMapping
    public List<MovieDto> allMovies() {
        return movieService.findAllMovies();
    }

    @GetMapping("/search")
    public List<MovieDto> searchByTitle(@RequestParam("title") String keyword) {
        return movieService.searchMoviesByTitle(keyword);
    }

    @GetMapping("/week")
    public List<MovieDto> weeklyByStatus(@RequestParam("movieStatus") String movieStatus) {
        return movieService.weeklyMoviesByStatus(movieStatus);
    }
}

package rut.miit.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rut.miit.cinema.dto.MovieAddDto;
import rut.miit.cinema.dto.MovieDto;
import rut.miit.cinema.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public void addMovie(@RequestBody MovieAddDto dto) {
        movieService.addNewMovie(dto);
    }

    @PutMapping("/{id}")
    public void updateMovie(@PathVariable Integer id, @RequestBody MovieAddDto movieDto) {
        movieService.updateMovieInfo(id, movieDto);
    }

    @GetMapping("/{id}")
    public MovieDto getMovieById(@PathVariable Integer id) {
        return movieService.getMovieDetails(id);
    }

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return movieService.findAllMovies();
    }

    @GetMapping("/search")
    public List<MovieDto> searchByTitle(@RequestParam("title") String keyword) {
        return movieService.searchMoviesByTitle(keyword);
    }

    @GetMapping("/week")
    public List<MovieDto> getWeeklyByStatus(@RequestParam("movieStatus") String movieStatus) {
        return movieService.weeklyMoviesByStatus(movieStatus);
    }
}

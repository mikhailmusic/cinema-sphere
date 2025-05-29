package rut.miit.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rut.miit.cinema.dto.GenreAddDto;
import rut.miit.cinema.dto.GenreDto;
import rut.miit.cinema.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private GenreService genreService;

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDto> allGenres() {
        return genreService.findAllGenres();
    }

    @PostMapping
    public void addGenre(@RequestBody GenreAddDto dto) {
        genreService.addGenre(dto);
    }

    @GetMapping("/{id}")
    public GenreDto getGenre(@PathVariable Integer id) {
        return genreService.findById(id);
    }
}


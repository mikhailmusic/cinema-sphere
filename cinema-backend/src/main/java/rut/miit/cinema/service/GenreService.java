package rut.miit.cinema.service;

import rut.miit.cinema.dto.GenreAddDto;
import rut.miit.cinema.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAllGenres();
    GenreDto findById(Integer id);
    void addGenre(GenreAddDto dto);
}

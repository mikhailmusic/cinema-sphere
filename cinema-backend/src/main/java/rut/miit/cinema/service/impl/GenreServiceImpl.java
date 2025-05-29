package rut.miit.cinema.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.cinema.dto.GenreAddDto;
import rut.miit.cinema.dto.GenreDto;
import rut.miit.cinema.entity.Genre;
import rut.miit.cinema.exception.NotFoundException;
import rut.miit.cinema.repository.GenreRepository;
import rut.miit.cinema.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    private GenreRepository genreRepository;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDto> findAllGenres() {
        return genreRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public GenreDto findById(Integer id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NotFoundException(Genre.class, id));
        return convertToDto(genre);
    }

    @Override
    public void addGenre(GenreAddDto dto) {
        Genre genre = new Genre(dto.getName());
        genreRepository.save(genre);
    }

    private GenreDto convertToDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}

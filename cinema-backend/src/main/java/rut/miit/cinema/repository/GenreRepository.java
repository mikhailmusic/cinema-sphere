package rut.miit.cinema.repository;

import rut.miit.cinema.entity.Genre;
import rut.miit.cinema.repository.generic.CreateRepository;
import rut.miit.cinema.repository.generic.ReadRepository;
import rut.miit.cinema.repository.generic.UpdateRepository;

import java.util.List;

public interface GenreRepository extends ReadRepository<Genre, Integer>, CreateRepository<Genre, Integer>, UpdateRepository<Genre, Integer> {

    List<Genre> findByNameContaining(String keyword);
}
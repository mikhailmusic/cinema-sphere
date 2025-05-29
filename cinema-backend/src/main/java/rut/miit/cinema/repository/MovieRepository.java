package rut.miit.cinema.repository;

import rut.miit.cinema.entity.Movie;
import rut.miit.cinema.entity.MovieStatus;
import rut.miit.cinema.repository.generic.CreateRepository;
import rut.miit.cinema.repository.generic.ReadRepository;
import rut.miit.cinema.repository.generic.UpdateRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public interface MovieRepository extends ReadRepository<Movie, Integer>, CreateRepository<Movie,Integer>, UpdateRepository<Movie,Integer> {

    List<Movie> findByStatuses(Set<MovieStatus> movieStatuses);
    List<Movie> findByTitleContaining(String keyword);
}

package rut.miit.cinema.repository;

import org.springframework.stereotype.Repository;
import rut.miit.cinema.entity.Movie;
import rut.miit.cinema.entity.Status;
import rut.miit.cinema.repository.generic.CreateRepository;
import rut.miit.cinema.repository.generic.ReadRepository;
import rut.miit.cinema.repository.generic.UpdateRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Repository
public interface MovieRepository extends ReadRepository<Movie, Integer>, CreateRepository<Movie,Integer>, UpdateRepository<Movie,Integer> {

    List<Movie> findByStatuses(Set<Status> statuses);
    List<Movie> findByTitleContaining(String keyword);
    List<Movie> findMoviesBetweenDates(LocalDate from, LocalDate to, Set<Status> statuses);
}

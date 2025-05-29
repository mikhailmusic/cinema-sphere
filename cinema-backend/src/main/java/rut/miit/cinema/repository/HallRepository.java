package rut.miit.cinema.repository;

import rut.miit.cinema.entity.Hall;
import rut.miit.cinema.repository.generic.CreateRepository;
import rut.miit.cinema.repository.generic.ReadRepository;
import rut.miit.cinema.repository.generic.UpdateRepository;

import java.util.List;

public interface HallRepository extends ReadRepository<Hall, Integer>, CreateRepository<Hall, Integer>, UpdateRepository<Hall, Integer> {

    List<Hall> findByNameContaining(String keyword);
}

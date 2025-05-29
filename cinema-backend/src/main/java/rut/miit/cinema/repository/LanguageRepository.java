package rut.miit.cinema.repository;

import rut.miit.cinema.entity.Language;
import rut.miit.cinema.repository.generic.CreateRepository;
import rut.miit.cinema.repository.generic.ReadRepository;
import rut.miit.cinema.repository.generic.UpdateRepository;

import java.util.List;

public interface LanguageRepository extends ReadRepository<Language, Integer>, CreateRepository<Language, Integer>, UpdateRepository<Language, Integer> {

    List<Language> findByCode(String code);
}

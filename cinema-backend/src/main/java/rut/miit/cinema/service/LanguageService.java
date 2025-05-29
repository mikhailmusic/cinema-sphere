package rut.miit.cinema.service;

import rut.miit.cinema.dto.LanguageAddDto;
import rut.miit.cinema.dto.LanguageDto;

import java.util.List;

public interface LanguageService {
    List<LanguageDto> findAllLanguages();
    LanguageDto findById(Integer id);
    void addLanguage(LanguageAddDto dto);
}

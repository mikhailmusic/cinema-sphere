package rut.miit.cinema.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.cinema.dto.LanguageAddDto;
import rut.miit.cinema.dto.LanguageDto;
import rut.miit.cinema.entity.Language;
import rut.miit.cinema.exception.NotFoundException;
import rut.miit.cinema.repository.LanguageRepository;
import rut.miit.cinema.service.LanguageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {
    private LanguageRepository languageRepository;

    @Autowired
    public void setLanguageRepository(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<LanguageDto> findAllLanguages() {
        return languageRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public LanguageDto findById(Integer id) {
        Language lang = languageRepository.findById(id).orElseThrow(() -> new NotFoundException(Language.class, id));
        return convertToDto(lang);
    }

    @Override
    public void addLanguage(LanguageAddDto dto) {
        Language language = new Language(dto.getName(), dto.getCode());
        languageRepository.save(language);
    }

    private LanguageDto convertToDto(Language lang) {
        return new LanguageDto(lang.getId(), lang.getName(), lang.getCode());
    }
}

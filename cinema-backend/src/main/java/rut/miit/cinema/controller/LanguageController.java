package rut.miit.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rut.miit.cinema.dto.LanguageAddDto;
import rut.miit.cinema.dto.LanguageDto;
import rut.miit.cinema.service.LanguageService;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {
    private LanguageService languageService;

    @Autowired
    public void setLanguageService(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public List<LanguageDto> allLanguages() {
        return languageService.findAllLanguages();
    }

    @PostMapping
    public void addLanguage(@RequestBody LanguageAddDto dto) {
        languageService.addLanguage(dto);
    }

    @GetMapping("/{id}")
    public LanguageDto getLanguage(@PathVariable Integer id) {
        return languageService.findById(id);
    }
}

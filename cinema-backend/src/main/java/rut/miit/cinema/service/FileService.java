package rut.miit.cinema.service;

import rut.miit.cinema.dto.ImageDto;

public interface FileService {
    String addImage(ImageDto dto);
    ImageDto getImage(String key);
}

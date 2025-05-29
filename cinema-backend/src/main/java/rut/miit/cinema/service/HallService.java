package rut.miit.cinema.service;

import rut.miit.cinema.dto.HallAddDto;
import rut.miit.cinema.dto.HallDto;

import java.util.List;

public interface HallService {
    List<HallDto> findAllHalls();
    HallDto findById(Integer id);
    void addHall(HallAddDto dto);
    void updateHall(Integer id, HallAddDto dto);
}

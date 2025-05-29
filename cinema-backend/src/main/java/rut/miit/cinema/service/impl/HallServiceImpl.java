package rut.miit.cinema.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.cinema.dto.HallAddDto;
import rut.miit.cinema.dto.HallDto;
import rut.miit.cinema.entity.Hall;
import rut.miit.cinema.exception.NotFoundException;
import rut.miit.cinema.repository.HallRepository;
import rut.miit.cinema.service.HallService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {
    private HallRepository hallRepository;

    @Autowired
    public void setHallRepository(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public List<HallDto> findAllHalls() {
        return hallRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public HallDto findById(Integer id) {
        Hall hall = hallRepository.findById(id).orElseThrow(() -> new NotFoundException(Hall.class, id));
        return convertToDto(hall);
    }

    @Override
    public void addHall(HallAddDto dto) {
        Hall hall = new Hall(dto.getName(), dto.getSeatCount());
        hallRepository.save(hall);
    }

    @Override
    public void updateHall(Integer id, HallAddDto dto) {
        Hall hall = hallRepository.findById(id).orElseThrow(() -> new NotFoundException(Hall.class, id));
        hall.setName(dto.getName());
        hall.setSeatCount(dto.getSeatCount());
        hallRepository.save(hall);
    }

    private HallDto convertToDto(Hall hall) {
        return new HallDto(hall.getId(), hall.getName(), hall.getSeatCount());
    }
}

package rut.miit.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rut.miit.cinema.dto.HallAddDto;
import rut.miit.cinema.dto.HallDto;
import rut.miit.cinema.service.HallService;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {
    private HallService hallService;

    @Autowired
    public void setHallService(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public List<HallDto> getAllHalls() {
        return hallService.findAllHalls();
    }

    @GetMapping("/{id}")
    public HallDto getHall(@PathVariable Integer id) {
        return hallService.findById(id);
    }

    @PostMapping
    public void addHall(@RequestBody HallAddDto dto) {
        hallService.addHall(dto);
    }

    @PutMapping("/{id}")
    public void updateHall(@PathVariable Integer id, @RequestBody HallAddDto dto) {
        hallService.updateHall(id, dto);
    }
}


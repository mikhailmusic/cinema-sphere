package rut.miit.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rut.miit.cinema.dto.SessionAddDto;
import rut.miit.cinema.dto.SessionDto;
import rut.miit.cinema.service.SessionService;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    private SessionService sessionService;

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<SessionDto> getAllSessions() {
        return sessionService.findAllSessions();
    }

    @PostMapping
    public void addSession(@RequestBody SessionAddDto dto) {
        sessionService.addSessionInfo(dto);
    }

    @PutMapping("/{id}")
    public void updateSession(@PathVariable Integer id, @RequestBody SessionAddDto dto) {
        sessionService.updateSessionInfo(id, dto);
    }

    @GetMapping("/{id}")
    public SessionDto getSession(@PathVariable Integer id) {
        return sessionService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Integer id) {
        sessionService.logicRemoveSessionInfo(id);
    }
}

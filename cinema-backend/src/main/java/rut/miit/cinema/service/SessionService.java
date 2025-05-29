package rut.miit.cinema.service;

import rut.miit.cinema.dto.SessionAddDto;
import rut.miit.cinema.dto.SessionDto;

import java.util.List;

public interface SessionService {
    List<SessionDto> findAllSessions();
    void createSession(SessionAddDto dto);
    void updateSession(Integer id, SessionAddDto dto);
    SessionDto findById(Integer id);
}

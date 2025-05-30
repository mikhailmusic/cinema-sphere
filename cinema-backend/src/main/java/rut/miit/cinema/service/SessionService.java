package rut.miit.cinema.service;

import rut.miit.cinema.dto.SessionAddDto;
import rut.miit.cinema.dto.SessionDto;

import java.util.List;

public interface SessionService {
    List<SessionDto> findAllSessions();
    SessionDto addSessionInfo(SessionAddDto dto);
    SessionDto updateSessionInfo(Integer id, SessionAddDto dto);
    void logicRemoveSessionInfo(Integer id);
    SessionDto findById(Integer id);
}

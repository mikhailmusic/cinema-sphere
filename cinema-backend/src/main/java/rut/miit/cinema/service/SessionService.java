package rut.miit.cinema.service;

import rut.miit.cinema.dto.SessionAddDto;
import rut.miit.cinema.dto.SessionDto;

import java.util.List;

public interface SessionService {
    List<SessionDto> findAllSessions();
    void addSessionInfo(SessionAddDto dto);
    void updateSessionInfo(Integer id, SessionAddDto dto);
    void logicRemoveSessionInfo(Integer id);
    SessionDto findById(Integer id);
}

package rut.miit.cinema.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.cinema.dto.SessionAddDto;
import rut.miit.cinema.dto.SessionDto;
import rut.miit.cinema.entity.*;
import rut.miit.cinema.exception.NotFoundException;
import rut.miit.cinema.repository.HallRepository;
import rut.miit.cinema.repository.MovieRepository;
import rut.miit.cinema.repository.SessionRepository;
import rut.miit.cinema.service.SessionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository;
    private MovieRepository movieRepository;
    private HallRepository hallRepository;

    @Autowired
    public void setSessionRepository(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Autowired
    public void setHallRepository(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public List<SessionDto> findAllSessions() {
        return sessionRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void createSession(SessionAddDto dto) {
        Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new NotFoundException(Movie.class, dto.getMovieId()));
        Hall hall = hallRepository.findById(dto.getHallId()).orElseThrow(() -> new NotFoundException(Hall.class, dto.getHallId()));

        Session session = new Session(movie, dto.getStartTime(), hall);
        sessionRepository.save(session);
    }

    @Override
    public void updateSession(Integer id, SessionAddDto dto) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new NotFoundException(Session.class, id));
        Hall hall = hallRepository.findById(dto.getHallId()).orElseThrow(() -> new NotFoundException(Hall.class, dto.getHallId()));
        session.setHall(hall);
        session.setStartTime(dto.getStartTime());
        session.setStatus(SessionStatus.valueOf(dto.getStatus()));
        sessionRepository.save(session);
    }

    @Override
    public SessionDto findById(Integer id) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new NotFoundException(Session.class, id));
        return convertToDto(session);
    }

    private SessionDto convertToDto(Session session) {
        return new SessionDto(session.getId(), session.getStartTime(), session.getStatus().name(),
                session.getHall().getName(), session.getHall().getSeatCount()
        );
    }
}

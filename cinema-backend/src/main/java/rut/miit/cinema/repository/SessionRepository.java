package rut.miit.cinema.repository;

import rut.miit.cinema.entity.Session;
import rut.miit.cinema.entity.SessionStatus;
import rut.miit.cinema.repository.generic.CreateRepository;
import rut.miit.cinema.repository.generic.ReadRepository;
import rut.miit.cinema.repository.generic.UpdateRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface SessionRepository extends ReadRepository<Session, Integer>, CreateRepository<Session, Integer>, UpdateRepository<Session, Integer> {

    List<Session> findByStatusIn(Set<SessionStatus> statuses);
    List<Session> findByStartTimeBetweenAndStatus(LocalDateTime from, LocalDateTime to, SessionStatus status);

}

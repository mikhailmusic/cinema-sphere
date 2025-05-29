package rut.miit.cinema.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.cinema.entity.Session;
import rut.miit.cinema.entity.SessionStatus;
import rut.miit.cinema.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public class SessionRepositoryImpl extends BaseRepository<Session, Integer> implements SessionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public SessionRepositoryImpl() {
        super(Session.class);
    }

    @Override
    public List<Session> findByStatusIn(Set<SessionStatus> statuses) {
        return entityManager.createQuery(
                        "SELECT s FROM Session s WHERE s.status IN :statuses", Session.class)
                .setParameter("statuses", statuses)
                .getResultList();
    }

    @Override
    public List<Session> findByStartTimeBetweenAndStatus(LocalDateTime from, LocalDateTime to, SessionStatus status) {
        return entityManager.createQuery(
                        "SELECT s FROM Session s WHERE s.startTime BETWEEN :from AND :to AND s.status = :status", Session.class)
                .setParameter("from", from).setParameter("to", to).setParameter("status", status)
                .getResultList();
    }
}

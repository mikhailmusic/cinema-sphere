package rut.miit.cinema.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.cinema.entity.Hall;
import rut.miit.cinema.repository.HallRepository;

import java.util.List;

@Repository
public class HallRepositoryImpl extends BaseRepository<Hall, Integer> implements HallRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public HallRepositoryImpl() {
        super(Hall.class);
    }

    @Override
    public List<Hall> findByNameContaining(String keyword) {
        return entityManager.createQuery(
                        "SELECT h FROM Hall h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%'))", Hall.class)
                .setParameter("keyword", keyword)
                .getResultList();
    }
}

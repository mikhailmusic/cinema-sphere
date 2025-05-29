package rut.miit.cinema.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.cinema.entity.Genre;
import rut.miit.cinema.repository.GenreRepository;

import java.util.List;

@Repository
public class GenreRepositoryImpl extends BaseRepository<Genre, Integer> implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public GenreRepositoryImpl() {
        super(Genre.class);
    }

    @Override
    public List<Genre> findByNameContaining(String keyword) {
        return entityManager.createQuery(
                        "SELECT g FROM Genre g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%'))", Genre.class)
                .setParameter("keyword", keyword)
                .getResultList();
    }
}

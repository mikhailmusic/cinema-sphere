package rut.miit.cinema.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import rut.miit.cinema.entity.Movie;
import rut.miit.cinema.entity.Status;
import rut.miit.cinema.repository.MovieRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Repository
public class MovieRepositoryImpl extends BaseRepository<Movie, Integer> implements MovieRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public MovieRepositoryImpl() {
        super(Movie.class);
    }

    @Override
    public List<Movie> findByStatuses(Set<Status> statuses) {
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.status IN :statuses", Movie.class)
                .setParameter("statuses", statuses).getResultList();
    }

    @Override
    public List<Movie> findByTitleContaining(String keyword) {
        return entityManager.createQuery(
                "SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%'))", Movie.class)
                .setParameter("keyword", keyword).getResultList();
    }

    @Override
    public List<Movie> findMoviesBetweenDates(LocalDate from, LocalDate to, Set<Status> statuses) {
        TypedQuery<Movie> query = entityManager.createQuery(
                "SELECT m FROM Movie m " +
                        "WHERE m.status IN :statuses " +
                        "AND m.startTime <= :to " +
                        "AND m.endTime >= :from", Movie.class
        );
        query.setParameter("from", from);
        query.setParameter("to", to);
        query.setParameter("statuses", statuses);

        return query.getResultList();
    }

}

package rut.miit.cinema.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.cinema.entity.Language;
import rut.miit.cinema.repository.LanguageRepository;

import java.util.List;

@Repository
public class LanguageRepositoryImpl extends BaseRepository<Language, Integer> implements LanguageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public LanguageRepositoryImpl() {
        super(Language.class);
    }

    @Override
    public List<Language> findByCode(String code) {
        return entityManager.createQuery(
                        "SELECT l FROM Language l WHERE LOWER(l.code) = LOWER(:code)", Language.class)
                .setParameter("code", code)
                .getResultList();
    }
}

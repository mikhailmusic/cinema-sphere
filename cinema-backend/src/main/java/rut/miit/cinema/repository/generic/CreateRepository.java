package rut.miit.cinema.repository.generic;

import org.springframework.data.repository.NoRepositoryBean;
import rut.miit.cinema.entity.BaseEntity;


@NoRepositoryBean
public interface CreateRepository<T extends BaseEntity, ID> {
    T save(T entity);
}

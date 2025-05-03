package rut.miit.cinema.repository.generic;

import org.springframework.data.repository.NoRepositoryBean;
import rut.miit.cinema.entity.BaseEntity;

@NoRepositoryBean
public interface UpdateRepository<T extends BaseEntity, ID> {
    T update(T entity);
}

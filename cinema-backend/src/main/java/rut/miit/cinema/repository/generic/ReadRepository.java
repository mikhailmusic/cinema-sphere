package rut.miit.cinema.repository.generic;

import org.springframework.data.repository.NoRepositoryBean;
import rut.miit.cinema.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadRepository<T extends BaseEntity, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
}

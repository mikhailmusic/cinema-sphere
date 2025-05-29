package rut.miit.cinema.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class<?> entityClass, Integer id) {
        super(entityClass.getSimpleName() + " with id " + id + " not found");
    }
}

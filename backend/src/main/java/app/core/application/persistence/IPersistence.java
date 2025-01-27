package app.core.application.persistence;

import jakarta.transaction.Transactional;

public interface IPersistence<T> {
    @Transactional
    void create(T entity);

    @Transactional
    void update(T entity);

    @Transactional
    void delete(T entity);

    @Transactional
    void remove(T entity);

    @Transactional
    void restore(T entity);
}

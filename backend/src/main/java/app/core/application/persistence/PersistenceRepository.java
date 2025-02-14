package app.core.application.persistence;

import app.core.application.QueryFilter;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;

@Dependent
public class PersistenceRepository<T> implements IPersistence<T> {

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void remove(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    @Transactional
    public void restore(T entity) {
        entityManager.merge(entity);
    }


    protected <T> List<T> listByQueryFilter(QueryFilter queryFilter, Class<T> dtoClass) {
        try {
            Query query = entityManager.createNativeQuery(queryFilter.getSql().toString(), dtoClass);
            queryFilter.getParams().forEach(query::setParameter);
            return (List<T>) query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    protected Integer countByQueryFilter(QueryFilter queryFilter) {
        try {
            Query query = entityManager.createNativeQuery(queryFilter.getCountSql().toString());
            queryFilter.getParams().forEach(query::setParameter);
            return ((Number) query.getSingleResult()).intValue();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    protected <T> T findFirstResult(QueryFilter queryFilter, Class<T> dtoClass) {
        try {
            Query query = entityManager.createNativeQuery(queryFilter.getSql().toString(), dtoClass);
            queryFilter.getParams().forEach(query::setParameter);
            query.setMaxResults(1);
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}

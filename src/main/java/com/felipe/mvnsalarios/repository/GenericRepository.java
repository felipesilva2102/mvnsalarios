package com.felipe.mvnsalarios.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GenericRepository<T, ID> {

    public List<T> findAll(Class<T> entityClass) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Optional<T> findById(Class<T> entityClass, ID id) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    public void deleteAll(Class<T> entityClass) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaDelete<T> criteriaDelete = criteriaBuilder.createCriteriaDelete(entityClass);
            criteriaDelete.from(entityClass);
            entityManager.createQuery(criteriaDelete).executeUpdate();
            tx.commit();
        }
    }

    public void deleteById(Class<T> entityClass, ID id) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        T entity = findById(entityClass, id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        entityManager.remove(entity);
    }

    public T save(T entity) {
        T entityBD;
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            if (entityManager.contains(entity)) {
                entityBD = entityManager.merge(entity); // Atualiza a entidade se já existe
            } else {
                entityManager.persist(entity); // Persiste a entidade se não existir
                entityBD = entity;
            }
            tx.commit();
        }
        return entityBD;
    }

    public List<T> saveAll(List<T> entities) {
        List<T> entitiesBD = new ArrayList<>();

        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();

            for (T entity : entities) {
                if (entityManager.contains(entity)) {
                    entitiesBD.add(entityManager.merge(entity));
                } else {
                    entityManager.persist(entity);
                    entitiesBD.add(entity);
                }
            }

            tx.commit();
        }

        return entitiesBD;
    }

    public T update(T entity) {
        T entityBD;
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            entityBD = entityManager.merge(entity);
            tx.commit();
        }
        return entityBD;
    }

}
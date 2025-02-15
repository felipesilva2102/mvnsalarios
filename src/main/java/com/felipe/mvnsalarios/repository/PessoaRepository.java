package com.felipe.mvnsalarios.repository;

import com.felipe.mvnsalarios.domain.Pessoa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class PessoaRepository extends GenericRepository<Pessoa, Integer> {

    @Override
    public List<Pessoa> findAll(Class<Pessoa> entityClass) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<Pessoa> root = criteriaQuery.from(entityClass);
        root.fetch("pessoaSalarioConsolidado", JoinType.LEFT);
//        Join<Pessoa, PessoaSalarioConsolidado> clienteJoin = root.join("pessoaSalarioConsolidado", JoinType.LEFT);
        criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get("id")));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void lastId() {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("SELECT setval('pessoa_seq', COALESCE((SELECT MAX(id) FROM pessoa), 1))").executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            JpaUtil.getEntityManager().getTransaction().rollback();
            e.printStackTrace();
        }
    }

}

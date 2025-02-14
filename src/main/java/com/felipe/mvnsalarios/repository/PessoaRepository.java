package com.felipe.mvnsalarios.repository;

import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class PessoaRepository extends GenericRepository<Pessoa, Integer> {

    @Override
    public List<Pessoa> findAll(Class<Pessoa> entityClass) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<Pessoa> root = criteriaQuery.from(entityClass);
        Join<Pessoa, PessoaSalarioConsolidado> clienteJoin = root.join("pessoaSalarioConsolidado", JoinType.LEFT);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
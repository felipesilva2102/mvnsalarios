package com.felipe.mvnsalarios.repository;

import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.domain.Vencimentos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class VencimentosRepository {

    public List<Vencimentos> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Vencimentos> cq = cb.createQuery(Vencimentos.class);
            Root<Vencimentos> root = cq.from(Vencimentos.class);
            cq.select(root);

            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

}

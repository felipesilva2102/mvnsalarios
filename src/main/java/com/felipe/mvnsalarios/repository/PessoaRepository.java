package com.felipe.mvnsalarios.repository;

import com.felipe.mvnsalarios.domain.Pessoa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class PessoaRepository {

    public Pessoa save(Pessoa pessoa) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Pessoa pessoaDB = em.merge(pessoa);
            tx.commit();
            return pessoaDB;
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

    public List<Pessoa> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Pessoa> cq = cb.createQuery(Pessoa.class);
            Root<Pessoa> root = cq.from(Pessoa.class);
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

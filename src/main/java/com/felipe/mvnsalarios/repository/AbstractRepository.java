/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.felipe.mvnsalarios.repository;

import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractRepository {

    public <T> List<T> findAll(Class<T> entityClass) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
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

    public <T> T save(T entity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            T entityBD = em.merge(entity);
            tx.commit();
            return entityBD;
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
    
    public void deleteAll() {
    EntityManager em = JpaUtil.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    
    try {
        tx.begin();
        
        // Criar um CriteriaBuilder
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        // Criar um CriteriaDelete para a entidade PessoaSalarioConsolidado
        CriteriaDelete<PessoaSalarioConsolidado> delete = cb.createCriteriaDelete(PessoaSalarioConsolidado.class);
        
        // Definir a raiz da deleção (não precisa de WHERE para deletar tudo)
        delete.from(PessoaSalarioConsolidado.class);
        
        // Executar a deleção
        em.createQuery(delete).executeUpdate();
        
        tx.commit();
    } catch (Exception e) {
        if (tx.isActive()) {
            tx.rollback();
        }
        e.printStackTrace();
    } finally {
        em.close();
    }
}

    public <T> T findOne(Class<T> entityClass, Object id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    public <T> T update(T entity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            T updatedEntity = em.merge(entity);
            tx.commit();
            return updatedEntity;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

}

package com.felipe.mvnsalarios.repository;

import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@ApplicationScoped
public class PessoaSalarioConsolidadoRepository {

    public PessoaSalarioConsolidado save(PessoaSalarioConsolidado pessoaSalarioConsolidado) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            PessoaSalarioConsolidado pessoaSalarioConsolidadoDB = em.merge(pessoaSalarioConsolidado);
            tx.commit();
            return pessoaSalarioConsolidadoDB;
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

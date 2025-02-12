package com.felipe.mvnsalarios.repository;

import com.felipe.mvnsalarios.domain.Cargo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@ApplicationScoped
public class CargoRepository {

    public Cargo save(Cargo cargo) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Cargo cargoBD = em.merge(cargo);
            tx.commit();
            return cargoBD;
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

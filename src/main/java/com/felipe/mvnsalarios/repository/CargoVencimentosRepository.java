package com.felipe.mvnsalarios.repository;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.CargoVencimentos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class CargoVencimentosRepository {

    public List<CargoVencimentos> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CargoVencimentos> cq = cb.createQuery(CargoVencimentos.class);
            Root<CargoVencimentos> root = cq.from(CargoVencimentos.class);
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

    public List<CargoVencimentos> findByCargo(Cargo cargo) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
                
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CargoVencimentos> query = cb.createQuery(CargoVencimentos.class);
        Root<CargoVencimentos> cargoVencimentos = query.from(CargoVencimentos.class);

        // Aplicando filtro WHERE nome = :nome
        query.select(cargoVencimentos).where(cb.equal(cargoVencimentos.get("cargo"), cargo));

        return em.createQuery(query).getResultList();
    }

}

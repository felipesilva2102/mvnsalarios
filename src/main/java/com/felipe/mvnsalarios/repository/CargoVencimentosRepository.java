package com.felipe.mvnsalarios.repository;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.CargoVencimentos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class CargoVencimentosRepository extends GenericRepository<CargoVencimentos, Integer> {

    public List<CargoVencimentos> findByCargo(Cargo cargo) {
        EntityManager em = JpaUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CargoVencimentos> query = cb.createQuery(CargoVencimentos.class);
        Root<CargoVencimentos> cargoVencimentos = query.from(CargoVencimentos.class);
        query.select(cargoVencimentos).where(cb.equal(cargoVencimentos.get("cargo"), cargo));
        return em.createQuery(query).getResultList();
    }

}
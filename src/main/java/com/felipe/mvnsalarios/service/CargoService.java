package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.repository.CargoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class CargoService {

    @Inject
    private CargoRepository cargoRepository;

    public Cargo save(Cargo cargo) {
        return cargoRepository.save(cargo);
    }
    
    public List<Cargo> findAll() {
        return cargoRepository.findAll(Cargo.class);
    }

    public Object findOne(Integer id) {
        return cargoRepository.findOne(Cargo.class, id);
    }

}

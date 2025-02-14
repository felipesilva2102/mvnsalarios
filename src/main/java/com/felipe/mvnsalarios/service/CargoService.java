package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.repository.CargoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public Cargo findById(Integer id) {
//        return cargoRepository.findOne(Cargo.class, id);
        Optional<Cargo> cargoOptional = cargoRepository.findById(Cargo.class, id);
        if (cargoOptional.isPresent()) {
            return cargoOptional.get();
        }
        return null;
    }

}
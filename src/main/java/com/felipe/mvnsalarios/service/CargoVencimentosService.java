package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.CargoVencimentos;
import com.felipe.mvnsalarios.repository.CargoVencimentosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CargoVencimentosService {

    @Inject
    private CargoVencimentosRepository cargoVencimentosRepository;

    public List<CargoVencimentos> findAll() {
        return cargoVencimentosRepository.findAll();
    }

    public List<CargoVencimentos> findByCargo(Cargo cargo) {
        return cargoVencimentosRepository.findByCargo(cargo);
    }

}

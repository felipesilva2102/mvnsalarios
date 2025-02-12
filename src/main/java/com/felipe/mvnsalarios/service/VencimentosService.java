package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.Vencimentos;
import com.felipe.mvnsalarios.repository.VencimentosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class VencimentosService {

    @Inject
    private VencimentosRepository vencimentosRepository;

    public List<Vencimentos> findAll() {
        return vencimentosRepository.findAll();
    }

}

package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import com.felipe.mvnsalarios.repository.PessoaSalarioConsolidadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PessoaSalarioConsolidadoService {

    @Inject
    private PessoaSalarioConsolidadoRepository pessoaSalarioConsolidadoRepository;
    
    @Transactional
    public PessoaSalarioConsolidado save(PessoaSalarioConsolidado pessoaSalarioConsolidado) {
        return pessoaSalarioConsolidadoRepository.save(pessoaSalarioConsolidado);
    }


}

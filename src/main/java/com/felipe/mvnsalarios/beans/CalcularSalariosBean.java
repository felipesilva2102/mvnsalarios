package com.felipe.mvnsalarios.beans;

import com.felipe.mvnsalarios.service.PessoaSalarioConsolidadoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CalcularSalariosBean {
    
    @Inject
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;

    public void calcularSalarios() {
        pessoaSalarioConsolidadoService.calcularSalarios();
    }
}

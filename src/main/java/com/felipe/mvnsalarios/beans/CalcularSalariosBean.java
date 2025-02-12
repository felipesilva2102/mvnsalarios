package com.felipe.mvnsalarios.beans;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.CargoVencimentos;
import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import com.felipe.mvnsalarios.domain.TipoVencimento;
import com.felipe.mvnsalarios.domain.Vencimentos;
import com.felipe.mvnsalarios.service.CargoService;
import com.felipe.mvnsalarios.service.CargoVencimentosService;
import com.felipe.mvnsalarios.service.PessoaSalarioConsolidadoService;
import com.felipe.mvnsalarios.service.PessoaService;
import com.felipe.mvnsalarios.service.VencimentosService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.util.List;

@Named
@RequestScoped
public class CalcularSalariosBean {

    @Inject
    private CargoService cargoService;
    
    @Inject
    private PessoaService pessoaService;
    
    @Inject
    private CargoVencimentosService cargoVencimentosService;
    
    @Inject
    private VencimentosService vencimentosService;

    @Inject
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;

    public void calcularSalarios(){
        List<Pessoa> pessoas = pessoaService.findAll();
        for (Pessoa pessoa : pessoas) {
            Cargo cargo = pessoa.getCargo();
            List<CargoVencimentos> cargoVencimentos = cargoVencimentosService.findByCargo(cargo);
            BigDecimal salarioCalculado = BigDecimal.ZERO;
            for (CargoVencimentos cargoVencimento : cargoVencimentos) {
                Vencimentos vencimentos = cargoVencimento.getVencimentos();
                if(vencimentos.getTipoVencimento().equals(TipoVencimento.CREDITO)){
                    salarioCalculado.add(vencimentos.getValor());
                }
                else {
                    salarioCalculado.subtract(vencimentos.getValor());
                }
            }
            PessoaSalarioConsolidado pessoaSalarioConsolidado = new PessoaSalarioConsolidado();
            pessoaSalarioConsolidado.setNomeCargo(cargo.getNome());
            pessoaSalarioConsolidado.setNomePessoa(pessoa.getNome());
            pessoaSalarioConsolidado.setPessoa(pessoa);
            pessoaSalarioConsolidado.setSalario(salarioCalculado);
            pessoaSalarioConsolidadoService.save(pessoaSalarioConsolidado);
        }
    }
}

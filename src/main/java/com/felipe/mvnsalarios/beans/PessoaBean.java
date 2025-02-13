package com.felipe.mvnsalarios.beans;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.service.CargoService;
import com.felipe.mvnsalarios.service.PessoaSalarioConsolidadoService;
import com.felipe.mvnsalarios.service.PessoaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class PessoaBean {

    @Inject
    private PessoaService pessoaService;
    
    @Inject
    private CargoService cargoService;

    @Inject
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;

    private List<Cargo> cargos =  new ArrayList<>();
    private List<Pessoa> pessoas = new ArrayList<>();
    private Pessoa pessoa = new Pessoa();

    public List<Cargo> getCargos() {
        if (cargos.isEmpty()) {
            cargos = cargoService.findAll();
        }
        return cargos;
    }
    
    public List<Pessoa> getPessoas() {
        if (pessoas.isEmpty()) {
            pessoas = pessoaService.findAll();
        }
        return pessoas;
    }

    public void salvar() {
        pessoaService.save(pessoa);
        pessoa = new Pessoa();
    }

    public void excluir(Pessoa Pessoa) {
        pessoas.remove(Pessoa);
    }

    public void prepararEdicao(Pessoa Pessoa) {
        this.pessoa = Pessoa;
    }

    public void prepararDetalhamento(Pessoa Pessoa) {
        this.pessoa = Pessoa;
    }

    public void calcularSalarios() {
        pessoaSalarioConsolidadoService.calcularSalarios();
    }
}

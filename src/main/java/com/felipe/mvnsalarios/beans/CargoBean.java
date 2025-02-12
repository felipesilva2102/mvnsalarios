package com.felipe.mvnsalarios.beans;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.service.CargoService;
import com.felipe.mvnsalarios.service.PessoaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CargoBean {

    private String nome;
    private boolean cargoCriado;

    @Inject
    private CargoService cargoService;
    
    @Inject
    private PessoaService pessoaService;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isCargoCriado() {
        return cargoCriado;
    }

    public void setCargoCriado(boolean cargoCriado) {
        this.cargoCriado = cargoCriado;
    }

    public String save() {
        List<Pessoa> listaPessoas = pessoaService.findAll();
        Cargo cargo = new Cargo();
        cargo.setNome(nome);
        cargo = cargoService.save(cargo);
        cargoCriado = true;
        return null;
    }
}

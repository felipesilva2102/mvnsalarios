package com.felipe.mvnsalarios.beans;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.service.CargoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CargoBean {

    private String nome;
    private boolean cargoCriado;

    @Inject
    private CargoService cargoService;

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
        Cargo cargo = new Cargo();
        cargo.setNome(nome);
        cargo = cargoService.save(cargo);
        cargoCriado = true;
        return null;
    }
}

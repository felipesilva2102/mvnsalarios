
package com.felipe.mvnsalarios.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.component.UIComponent;
import jakarta.faces.convert.Converter;
import java.util.List;
import java.util.Objects;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.service.CargoService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(value = "cargoConverter", managed = true)
public class CargoConverter implements Converter<Cargo> {

    private List<Cargo> listaCargos;

    @Inject
    private CargoService cargoService;

    @PostConstruct
    public void postConstruct() {
        this.listaCargos = cargoService.findAll();
    }

    @Override
    public Cargo getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return listaCargos.stream()
                .filter(obj -> Objects.equals(obj.getId().toString(), value))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Cargo cargo) {
        if (cargo == null || cargo.getId() == null) {
            return "";
        }
        return cargo.getId().toString();
    }

}

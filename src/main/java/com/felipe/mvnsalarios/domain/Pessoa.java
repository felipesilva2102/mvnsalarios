/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.felipe.mvnsalarios.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String pais;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @OneToOne(mappedBy = "pessoa", fetch = FetchType.LAZY)
    private PessoaSalarioConsolidado pessoaSalarioConsolidado;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    public PessoaSalarioConsolidado calcularSalarioConsolidado(List<CargoVencimentos> cargoVencimentos) {
        PessoaSalarioConsolidado pessoaSalarioConsolidadoCalculado = new PessoaSalarioConsolidado();

        BigDecimal salarioCalculado = BigDecimal.ZERO;
        for (CargoVencimentos cargoVencimento : cargoVencimentos) {
            if (!this.cargo.getId().equals(cargoVencimento.getCargo().getId())) {
                throw new DominioException("Não é possível calcular o salário de " + this.getNome() + ". "
                        + "O cargo do vencimento informado (" + cargoVencimento.getCargo().getNome() + ") está diferente do cargo da pessoa (" + this.getCargo().getNome() + ") ");
            }

            Vencimentos vencimentos = cargoVencimento.getVencimentos();
            if (vencimentos.getTipoVencimento().equals(TipoVencimento.CREDITO)) {
                salarioCalculado = salarioCalculado.add(vencimentos.getValor());
            } else {
                salarioCalculado = salarioCalculado.subtract(vencimentos.getValor());
            }
        }
        pessoaSalarioConsolidadoCalculado.setNomeCargo(this.getCargo().getNome());
        pessoaSalarioConsolidadoCalculado.setNomePessoa(this.getNome());
        pessoaSalarioConsolidadoCalculado.setPessoa(this);
        pessoaSalarioConsolidadoCalculado.setSalario(salarioCalculado);

        return pessoaSalarioConsolidadoCalculado;
    }
}

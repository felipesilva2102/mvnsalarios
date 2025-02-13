package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.CargoVencimentos;
import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import com.felipe.mvnsalarios.domain.TipoVencimento;
import com.felipe.mvnsalarios.domain.Vencimentos;
import com.felipe.mvnsalarios.repository.PessoaSalarioConsolidadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Transactional
@Slf4j
public class PessoaSalarioConsolidadoService {

    @Inject
    private PessoaSalarioConsolidadoRepository pessoaSalarioConsolidadoRepository;

    @Inject
    private CargoService cargoService;

    @Inject
    private PessoaService pessoaService;

    @Inject
    private CargoVencimentosService cargoVencimentosService;

    @Inject
    private VencimentosService vencimentosService;

    public PessoaSalarioConsolidado save(PessoaSalarioConsolidado pessoaSalarioConsolidado) {
        return pessoaSalarioConsolidadoRepository.save(pessoaSalarioConsolidado);
    }

    public void calcularSalariosService() {
        pessoaSalarioConsolidadoRepository.deleteAll(PessoaSalarioConsolidado.class);
        List<Pessoa> pessoas = pessoaService.findAll();
        int qtdPessoas = pessoas.size();
        int contador = 1;
        for (Pessoa pessoa : pessoas) {
            Cargo cargo = pessoa.getCargo();
            List<CargoVencimentos> cargoVencimentos = cargoVencimentosService.findByCargo(cargo);
            BigDecimal salarioCalculado = BigDecimal.ZERO;
            for (CargoVencimentos cargoVencimento : cargoVencimentos) {
                Vencimentos vencimentos = cargoVencimento.getVencimentos();
                if (vencimentos.getTipoVencimento().equals(TipoVencimento.CREDITO)) {
                    salarioCalculado = salarioCalculado.add(vencimentos.getValor());
                } else {
                    salarioCalculado = salarioCalculado.subtract(vencimentos.getValor());
                }
            }
            PessoaSalarioConsolidado pessoaSalarioConsolidado = new PessoaSalarioConsolidado();
            pessoaSalarioConsolidado.setNomeCargo(cargo.getNome());
            pessoaSalarioConsolidado.setNomePessoa(pessoa.getNome());
            pessoaSalarioConsolidado.setPessoa(pessoa);
            pessoaSalarioConsolidado.setSalario(salarioCalculado);
            log.info("... " + contador + "/" + qtdPessoas + " - salário calculado de " + pessoa.getNome() + ": " + salarioCalculado);
            save(pessoaSalarioConsolidado);
            contador++;
        }
    }

}

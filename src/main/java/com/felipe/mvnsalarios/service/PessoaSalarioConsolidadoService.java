package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.CargoVencimentos;
import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import com.felipe.mvnsalarios.repository.PessoaSalarioConsolidadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
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

    @Inject
    private ExecutorService executorService;

    public PessoaSalarioConsolidado save(PessoaSalarioConsolidado pessoaSalarioConsolidado) {
        return pessoaSalarioConsolidadoRepository.save(pessoaSalarioConsolidado);
    }

    public void calcularSalarios() {
        pessoaSalarioConsolidadoRepository.deleteAll(PessoaSalarioConsolidado.class);
        List<Pessoa> pessoas = pessoaService.findAll();
        List<PessoaSalarioConsolidado> salarios = new ArrayList<>();
        int qtdPessoas = pessoas.size();
        int contador = 0;

        for (Pessoa p : pessoas) {
            p.setCargo(cargoService.findById(p.getCargo().getId()));
            salarios.add(p.calcularSalarioConsolidado(cargoVencimentosService.findByCargo(p.getCargo())));
            log.info("... " + (contador + 1) + "/" + qtdPessoas + " - salário calculado de " + p.getNome() + ": " + salarios.get(contador).getSalario());
            contador++;
        }

        pessoaSalarioConsolidadoRepository.saveAll(salarios);
    }

    public Future<String> calcularSalariosAssincrono() {
        CompletableFuture.runAsync(() -> calcularSalarios(), executorService);
        String resultado = "Cálculo dos salários concluído!";
        log.info(resultado);
        return CompletableFuture.completedFuture(resultado);
    }

    public void deleteAll() {
        pessoaSalarioConsolidadoRepository.deleteAll(PessoaSalarioConsolidado.class);
    }

    public List<PessoaSalarioConsolidado> findAll() {
        return pessoaSalarioConsolidadoRepository.findAll(PessoaSalarioConsolidado.class);
    }

    public void calcularSalario(Pessoa pessoa) {
        pessoa.setCargo(cargoService.findById(pessoa.getCargo().getId()));
        PessoaSalarioConsolidado pessoaCalculada = pessoa.calcularSalarioConsolidado(cargoVencimentosService.findByCargo(pessoa.getCargo()));
        pessoaSalarioConsolidadoRepository.save(pessoaCalculada);
    }

}

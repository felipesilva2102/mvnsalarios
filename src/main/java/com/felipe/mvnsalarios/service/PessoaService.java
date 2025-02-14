package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import com.felipe.mvnsalarios.repository.PessoaRepository;
import com.felipe.mvnsalarios.repository.PessoaSalarioConsolidadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class PessoaService {

    @Inject
    private PessoaRepository pessoaRepository;

    @Inject
    private PessoaSalarioConsolidadoRepository pessoaSalarioConsolidadoRepository;

    public Pessoa save(Pessoa pessoa) {
        if (pessoa.getId() != null) {
            return pessoaRepository.update(pessoa);
        } else {
            return pessoaRepository.save(pessoa);
        }

    }

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll(Pessoa.class);
    }

    public void deleteById(Pessoa pessoa) {
        if (pessoa.getPessoaSalarioConsolidado() != null) {
            pessoaSalarioConsolidadoRepository.deleteById(PessoaSalarioConsolidado.class, pessoa.getPessoaSalarioConsolidado().getId());
        }
        pessoaRepository.deleteById(Pessoa.class, pessoa.getId());
    }

}

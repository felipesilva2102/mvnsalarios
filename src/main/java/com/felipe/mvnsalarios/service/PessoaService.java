package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.repository.PessoaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PessoaService {

    @Inject
    private PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

}

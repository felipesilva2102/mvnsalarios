package com.felipe.mvnsalarios.beans;

import com.felipe.mvnsalarios.domain.Pessoa;
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

    private List<Pessoa> itens = new ArrayList<>();
    private Pessoa pessoa = new Pessoa();

    public List<Pessoa> getItens() {
        return itens;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void salvar() {
        if (pessoa.getId() == null) {

        } else {

        }
        pessoa = new Pessoa(); // Limpar formulário
    }

    public void excluir(Pessoa Pessoa) {
        itens.remove(Pessoa);
    }

    public void prepararEdicao(Pessoa Pessoa) {
        this.pessoa = Pessoa;
    }

    public void prepararDetalhamento(Pessoa Pessoa) {
        this.pessoa = Pessoa;
    }
}

package com.felipe.mvnsalarios;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.CargoVencimentos;
import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import com.felipe.mvnsalarios.domain.TipoVencimento;
import com.felipe.mvnsalarios.domain.Vencimentos;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PessoaBeanTest {

    public Pessoa construirPessoaGenerica() {
        Pessoa pessoa = new Pessoa();

        pessoa.setNome("João da Silva");
        pessoa.setCidade("São Paulo");
        pessoa.setEmail("joao.silva@email.com");
        pessoa.setCep("01000-000");
        pessoa.setEndereco("Rua das Flores, Nº 123");
        pessoa.setUsuario("joaosilva");
        pessoa.setTelefone("(11) 91234-5678");
        pessoa.setPais("Brasil");
        pessoa.setDataNascimento(LocalDate.of(1990, 5, 20));
        Cargo cargo = new Cargo();
        cargo.setNome("Desenvolvedor");
        pessoa.setCargo(cargo);

        return pessoa;
    }

    public Vencimentos construirVencimentosGenericos() {
        Vencimentos vencimentos = new Vencimentos();

        vencimentos.setDescricao("Salário Base");
        vencimentos.setValor(new BigDecimal("2500.00"));
        vencimentos.setTipoVencimento(TipoVencimento.CREDITO);

        return vencimentos;
    }

    public CargoVencimentos construirCargoVencimentosGenerico(int i) {
        CargoVencimentos cargoVencimentos = new CargoVencimentos();
        Cargo cargo = new Cargo();
        cargo.setNome("Analista de Sistemas" + i);
        cargoVencimentos.setCargo(cargo);
        cargoVencimentos.setVencimentos(construirVencimentosGenericos());

        return cargoVencimentos;
    }

    public Vencimentos construirVencimentos(BigDecimal valor, TipoVencimento tipo) {
        Vencimentos vencimentos = new Vencimentos();
        vencimentos.setDescricao("Vencimento Teste");
        vencimentos.setValor(valor);
        vencimentos.setTipoVencimento(tipo);
        return vencimentos;
    }

    public CargoVencimentos construirCargoVencimentos(int i, BigDecimal valor, TipoVencimento tipo) {
        CargoVencimentos cargoVencimentos = new CargoVencimentos();
        Cargo cargo = new Cargo();
        cargo.setNome("Analista de Sistemas" + i);
        cargoVencimentos.setCargo(cargo);
        cargoVencimentos.setVencimentos(construirVencimentos(valor, tipo));
        return cargoVencimentos;
    }

    @Test
    void testCalculoSalarioPreenchido() {
        Pessoa p = construirPessoaGenerica();
        List<CargoVencimentos> cargosVencimentos = new ArrayList<>();
        List<PessoaSalarioConsolidado> consolidado = new ArrayList<>();

        for (int j = 0; j < 50; j++) {
            cargosVencimentos.add(construirCargoVencimentosGenerico(j));
        }

        consolidado.add(p.calcularSalarioConsolidado(cargosVencimentos));
        Assertions.assertNotNull(p);
        Assertions.assertNotNull(cargosVencimentos);
        Assertions.assertNotNull(consolidado);
    }

    @Test
    void testCalculoSalarioComDebito() {
        Pessoa p = construirPessoaGenerica();
        List<CargoVencimentos> cargosVencimentos = new ArrayList<>();

        cargosVencimentos.add(construirCargoVencimentos(1, new BigDecimal("3000.00"), TipoVencimento.CREDITO));
        cargosVencimentos.add(construirCargoVencimentos(2, new BigDecimal("1000.00"), TipoVencimento.DEBITO));

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargosVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(new BigDecimal("2000.00"), consolidado.getSalario());
    }

    @Test
    void testCalculoSalarioZerado() {
        Pessoa p = construirPessoaGenerica();
        List<CargoVencimentos> cargosVencimentos = new ArrayList<>();

        cargosVencimentos.add(construirCargoVencimentos(1, new BigDecimal("1500.00"), TipoVencimento.CREDITO));
        cargosVencimentos.add(construirCargoVencimentos(2, new BigDecimal("1500.00"), TipoVencimento.DEBITO));

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargosVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(BigDecimal.ZERO.doubleValue(), consolidado.getSalario().doubleValue());
    }

    @Test
    void testCalculoSalarioComDebitoUnico() {
        Pessoa p = construirPessoaGenerica();
        List<CargoVencimentos> cargosVencimentos = new ArrayList<>();

        cargosVencimentos.add(construirCargoVencimentos(1, new BigDecimal("5000.00"), TipoVencimento.CREDITO));
        cargosVencimentos.add(construirCargoVencimentos(2, new BigDecimal("1000.00"), TipoVencimento.DEBITO));

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargosVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(new BigDecimal("4000.00"), consolidado.getSalario());
    }

    @Test
    void testCalculoSalarioComCreditosEDebitos() {
        Pessoa p = construirPessoaGenerica();
        List<CargoVencimentos> cargosVencimentos = new ArrayList<>();

        cargosVencimentos.add(construirCargoVencimentos(1, new BigDecimal("3000.00"), TipoVencimento.CREDITO));
        cargosVencimentos.add(construirCargoVencimentos(2, new BigDecimal("1000.00"), TipoVencimento.CREDITO));
        cargosVencimentos.add(construirCargoVencimentos(3, new BigDecimal("500.00"), TipoVencimento.DEBITO));

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargosVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(new BigDecimal("3500.00"), consolidado.getSalario());
    }

    @Test
    void testCalculoSalarioComDebitoMaiorQueCredito() {
        Pessoa p = construirPessoaGenerica();
        List<CargoVencimentos> cargosVencimentos = new ArrayList<>();

        cargosVencimentos.add(construirCargoVencimentos(1, new BigDecimal("1000.00"), TipoVencimento.CREDITO));
        cargosVencimentos.add(construirCargoVencimentos(2, new BigDecimal("2000.00"), TipoVencimento.DEBITO));

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargosVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(new BigDecimal("-1000.00"), consolidado.getSalario());
    }

    @Test
    void testCalculoSalarioSemVencimentos() {
        Pessoa p = construirPessoaGenerica();
        List<CargoVencimentos> cargosVencimentos = new ArrayList<>();

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargosVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(BigDecimal.ZERO.doubleValue(), consolidado.getSalario().doubleValue());
    }

}

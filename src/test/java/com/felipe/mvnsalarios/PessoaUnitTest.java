package com.felipe.mvnsalarios;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.CargoVencimentos;
import com.felipe.mvnsalarios.domain.DominioException;
import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import com.felipe.mvnsalarios.domain.TipoVencimento;
import com.felipe.mvnsalarios.domain.Vencimentos;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PessoaUnitTest {

    private static List<Cargo> cargos;
    private static List<Vencimentos> vencimentos;
    private static List<CargoVencimentos> cargosVencimentos;

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
        return pessoa;
    }

    public Vencimentos construirVencimentosGenericos() {
        Vencimentos vencimentos = new Vencimentos();

        vencimentos.setDescricao("Salário Base");
        vencimentos.setValor(new BigDecimal("2500.00"));
        vencimentos.setTipoVencimento(TipoVencimento.CREDITO);

        return vencimentos;
    }

    public CargoVencimentos construirCargoVencimentosGenerico() {
        CargoVencimentos cargoVencimentos = new CargoVencimentos();
        Cargo cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setId(Integer.valueOf("1"));
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

    @BeforeAll
    static void setup() {
        cargos = new ArrayList<>();
        cargos.add(construirCargo(1, "Estagiario"));
        cargos.add(construirCargo(2, "Tecnico"));
        cargos.add(construirCargo(3, "Analista"));
        cargos.add(construirCargo(4, "Coordenador"));
        cargos.add(construirCargo(5, "Gerente"));
        cargos.add(construirCargo(6, "Pedreiro"));
        cargos.add(construirCargo(7, "Porteiro"));

        vencimentos = new ArrayList<>();
        vencimentos.add(construirVencimentos(1, "Vencimento Basico Estagiario", TipoVencimento.CREDITO, new BigDecimal("400.00")));
        vencimentos.add(construirVencimentos(2, "Vencimento Basico Tecnico", TipoVencimento.CREDITO, new BigDecimal("1000.00")));
        vencimentos.add(construirVencimentos(3, "Vencimento Basico Analista", TipoVencimento.CREDITO, new BigDecimal("2500.00")));
        vencimentos.add(construirVencimentos(4, "Vencimento Basico Coordenador", TipoVencimento.CREDITO, new BigDecimal("5000.00")));
        vencimentos.add(construirVencimentos(5, "Vencimento Basico Gerente", TipoVencimento.CREDITO, new BigDecimal("6500.00")));
        vencimentos.add(construirVencimentos(6, "Gratificacao Coordenador", TipoVencimento.CREDITO, new BigDecimal("500.00")));
        vencimentos.add(construirVencimentos(7, "Gratificacao Gerente", TipoVencimento.CREDITO, new BigDecimal("1000.00")));
        vencimentos.add(construirVencimentos(8, "Plano de Saude", TipoVencimento.DEBITO, new BigDecimal("350.00")));
        vencimentos.add(construirVencimentos(9, "Previdencia", TipoVencimento.DEBITO, new BigDecimal("11.00")));
        vencimentos.add(construirVencimentos(10, "Vencimento Basico Porteiro", TipoVencimento.CREDITO, new BigDecimal("8350.77")));

        cargosVencimentos = new ArrayList<>();
        cargosVencimentos.add(construirCargoVencimentos(1, 1, 1));
        cargosVencimentos.add(construirCargoVencimentos(2, 2, 2));
        cargosVencimentos.add(construirCargoVencimentos(3, 2, 9));
        cargosVencimentos.add(construirCargoVencimentos(4, 2, 8));
        cargosVencimentos.add(construirCargoVencimentos(5, 3, 3));
        cargosVencimentos.add(construirCargoVencimentos(6, 3, 9));
        cargosVencimentos.add(construirCargoVencimentos(7, 3, 8));
        cargosVencimentos.add(construirCargoVencimentos(8, 4, 4));
        cargosVencimentos.add(construirCargoVencimentos(9, 4, 9));
        cargosVencimentos.add(construirCargoVencimentos(10, 4, 8));
        cargosVencimentos.add(construirCargoVencimentos(11, 4, 6));
        cargosVencimentos.add(construirCargoVencimentos(12, 5, 5));
        cargosVencimentos.add(construirCargoVencimentos(13, 5, 9));
        cargosVencimentos.add(construirCargoVencimentos(14, 5, 8));
        cargosVencimentos.add(construirCargoVencimentos(15, 5, 7));
        cargosVencimentos.add(construirCargoVencimentos(16, 7, 9));
        cargosVencimentos.add(construirCargoVencimentos(17, 7, 10));
    }

    static Cargo construirCargo(Integer id, String nome) {
        Cargo c = new Cargo();
        c.setId(id);
        c.setNome(nome);
        return c;
    }

    static Vencimentos construirVencimentos(Integer id, String descricao, TipoVencimento tipoVencimento, BigDecimal valor) {
        Vencimentos v = new Vencimentos();
        v.setId(id);
        v.setDescricao(descricao);
        v.setTipoVencimento(tipoVencimento);
        v.setValor(valor);
        return v;
    }

    static CargoVencimentos construirCargoVencimentos(Integer id, Integer idCargo, Integer idVencimentos) {
        CargoVencimentos cargoVencimentos = new CargoVencimentos();
        cargoVencimentos.setId(id);
        cargoVencimentos.setCargo(cargos.stream().filter(c -> c.getId().equals(idCargo)).findFirst().get());
        cargoVencimentos.setVencimentos(vencimentos.stream().filter(v -> v.getId().equals(idVencimentos)).findFirst().get());
        return cargoVencimentos;
    }

    private Pessoa construirPessoaAtributosMinimos(Integer id, String nome, Cargo cargo) {
        Pessoa p = new Pessoa();
        p.setId(id);
        p.setNome(nome);
        p.setCargo(cargo);
        return p;
    }

    private List<CargoVencimentos> getVencimentosByCargo(Cargo cargo) {
        return cargosVencimentos.stream().filter(cv -> cv.getCargo().equals(cargo)).toList();
    }

    @Test
    void testSalarioEsperadoEstagiario() {
        Pessoa pessoa = construirPessoaAtributosMinimos(1, "joão", cargos.stream().filter(c -> c.getNome().equals("Estagiario")).findFirst().get());
        PessoaSalarioConsolidado salarioConsolidado = pessoa.calcularSalarioConsolidado(getVencimentosByCargo(pessoa.getCargo()));
        Assertions.assertNotNull(salarioConsolidado);
        Assertions.assertEquals(new BigDecimal("400.00"), salarioConsolidado.getSalario());
    }

    @Test
    void testSalarioEsperadoGerente() {
        Pessoa pessoa = construirPessoaAtributosMinimos(1, "joão", cargos.stream().filter(c -> c.getNome().equals("Gerente")).findFirst().get());
        PessoaSalarioConsolidado salarioConsolidado = pessoa.calcularSalarioConsolidado(getVencimentosByCargo(pessoa.getCargo()));
        Assertions.assertNotNull(salarioConsolidado);
        Assertions.assertEquals(new BigDecimal("7139.00"), salarioConsolidado.getSalario());
    }

    @Test
    void testSalarioEsperadoCoordenador() {
        Pessoa pessoa = construirPessoaAtributosMinimos(1, "joão", cargos.stream().filter(c -> c.getNome().equals("Coordenador")).findFirst().get());
        PessoaSalarioConsolidado salarioConsolidado = pessoa.calcularSalarioConsolidado(getVencimentosByCargo(pessoa.getCargo()));
        Assertions.assertNotNull(salarioConsolidado);
        Assertions.assertEquals(new BigDecimal("5139.00"), salarioConsolidado.getSalario());
    }

    @Test
    void testSalarioEsperadoTecnico() {
        Pessoa pessoa = construirPessoaAtributosMinimos(1, "joão", cargos.stream().filter(c -> c.getNome().equals("Tecnico")).findFirst().get());
        PessoaSalarioConsolidado salarioConsolidado = pessoa.calcularSalarioConsolidado(getVencimentosByCargo(pessoa.getCargo()));
        Assertions.assertNotNull(salarioConsolidado);
        Assertions.assertEquals(new BigDecimal("639.00"), salarioConsolidado.getSalario());
    }

    @Test
    void testSalarioEsperadoAnalista() {
        Pessoa pessoa = construirPessoaAtributosMinimos(1, "joão", cargos.stream().filter(c -> c.getNome().equals("Analista")).findFirst().get());
        PessoaSalarioConsolidado salarioConsolidado = pessoa.calcularSalarioConsolidado(getVencimentosByCargo(pessoa.getCargo()));
        Assertions.assertNotNull(salarioConsolidado);
        Assertions.assertEquals(new BigDecimal("2139.00"), salarioConsolidado.getSalario());
    }

    @Test
    void testSalarioEsperadoPorteiro() {
        Pessoa pessoa = construirPessoaAtributosMinimos(1, "joão", cargos.stream().filter(c -> c.getNome().equals("Porteiro")).findFirst().get());
        PessoaSalarioConsolidado salarioConsolidado = pessoa.calcularSalarioConsolidado(getVencimentosByCargo(pessoa.getCargo()));
        Assertions.assertNotNull(salarioConsolidado);
        Assertions.assertEquals(new BigDecimal("8339.77"), salarioConsolidado.getSalario());
    }

    @Test
    void testSalarioCalculadoErrado() {
        Pessoa pessoa = construirPessoaAtributosMinimos(1, "joão", cargos.stream().filter(c -> c.getNome().equals("Porteiro")).findFirst().get());
        PessoaSalarioConsolidado salarioConsolidado = pessoa.calcularSalarioConsolidado(getVencimentosByCargo(pessoa.getCargo()));
        Assertions.assertNotNull(salarioConsolidado);
        Assertions.assertNotEquals(new BigDecimal("100.00"), salarioConsolidado.getSalario());
    }

    @Test
    void testSalarioEsperadoCargoSemVencimentosAssociados() {
        Pessoa pessoa = construirPessoaAtributosMinimos(1, "joão", cargos.stream().filter(c -> c.getNome().equals("Pedreiro")).findFirst().get());
        PessoaSalarioConsolidado salarioConsolidado = pessoa.calcularSalarioConsolidado(getVencimentosByCargo(pessoa.getCargo()));
        Assertions.assertNotNull(salarioConsolidado);
        Assertions.assertEquals(BigDecimal.ZERO, salarioConsolidado.getSalario());
    }

    @Test
    void testSalarioNaoPodeSerCalculadoComVencimentosNaoPertencentesAoCargo() {
        Pessoa pessoa = construirPessoaAtributosMinimos(1, "joão", cargos.stream().filter(c -> c.getNome().equals("Porteiro")).findFirst().get());
        List<CargoVencimentos> vencimentosValidosPorteiro = cargosVencimentos.stream().filter(v -> v.getCargo().getId().equals(7)).toList();
        List<CargoVencimentos> vencimentosValidosAnalista = cargosVencimentos.stream().filter(v -> v.getCargo().getId().equals(3)).toList();
        List<CargoVencimentos> vencimentosMisturados = new ArrayList<>();
        vencimentosMisturados.addAll(vencimentosValidosPorteiro);
        vencimentosMisturados.addAll(vencimentosValidosAnalista);
        Assertions.assertThrows(DominioException.class,
                () -> pessoa.calcularSalarioConsolidado(vencimentosMisturados),
                "Não é possível calcular o salário de joão. O cargo do vencimento informado (Analista) está diferente do cargo da pessoa (Porteiro)");
    }

    @Test
    void testCalculoSalarioPreenchido() {
        Pessoa p = construirPessoaGenerica();
        List<CargoVencimentos> cargosVencimentos = new ArrayList<>();
        List<PessoaSalarioConsolidado> consolidado = new ArrayList<>();

        Cargo cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setId(Integer.valueOf("1"));

        p.setCargo(cargo);

        for (int j = 0; j < 50; j++) {
            cargosVencimentos.add(construirCargoVencimentosGenerico());
        }

        consolidado.add(p.calcularSalarioConsolidado(cargosVencimentos));
        Assertions.assertNotNull(p);
        Assertions.assertNotNull(cargosVencimentos);
        Assertions.assertNotNull(consolidado);
    }

    @Test
    void testCalculoSalarioComDebito() {
        Pessoa p = construirPessoaGenerica();
        CargoVencimentos cargoVencimento1 = new CargoVencimentos();
        CargoVencimentos cargoVencimento2 = new CargoVencimentos();
        List<CargoVencimentos> cargoVencimentos = new ArrayList<>();

        Cargo cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setId(Integer.valueOf("1"));

        p.setCargo(cargo);

        Vencimentos v1 = new Vencimentos();
        v1.setDescricao("v1");
        v1.setId(Integer.valueOf("1"));
        v1.setTipoVencimento(TipoVencimento.CREDITO);
        v1.setValor(new BigDecimal("3000.00"));

        Vencimentos v2 = new Vencimentos();
        v2.setDescricao("v1");
        v2.setId(Integer.valueOf("1"));
        v2.setTipoVencimento(TipoVencimento.DEBITO);
        v2.setValor(new BigDecimal("1000.00"));

        cargoVencimento1.setCargo(cargo);
        cargoVencimento1.setId(Integer.valueOf("1"));
        cargoVencimento1.setVencimentos(v1);
        cargoVencimentos.add(cargoVencimento1);

        cargoVencimento2.setCargo(cargo);
        cargoVencimento2.setId(Integer.valueOf("1"));
        cargoVencimento2.setVencimentos(v2);
        cargoVencimentos.add(cargoVencimento2);

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargoVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(new BigDecimal("2000.00"), consolidado.getSalario());
    }

    @Test
    void testCalculoSalarioZerado() {
        Pessoa p = construirPessoaGenerica();
        CargoVencimentos cargoVencimento1 = new CargoVencimentos();
        CargoVencimentos cargoVencimento2 = new CargoVencimentos();
        List<CargoVencimentos> cargoVencimentos = new ArrayList<>();

        Cargo cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setId(Integer.valueOf("1"));

        p.setCargo(cargo);

        Vencimentos v1 = new Vencimentos();
        v1.setDescricao("v1");
        v1.setId(Integer.valueOf("1"));
        v1.setTipoVencimento(TipoVencimento.CREDITO);
        v1.setValor(new BigDecimal("1000.00"));

        Vencimentos v2 = new Vencimentos();
        v2.setDescricao("v1");
        v2.setId(Integer.valueOf("1"));
        v2.setTipoVencimento(TipoVencimento.DEBITO);
        v2.setValor(new BigDecimal("1000.00"));

        cargoVencimento1.setCargo(cargo);
        cargoVencimento1.setId(Integer.valueOf("1"));
        cargoVencimento1.setVencimentos(v1);
        cargoVencimentos.add(cargoVencimento1);

        cargoVencimento2.setCargo(cargo);
        cargoVencimento2.setId(Integer.valueOf("1"));
        cargoVencimento2.setVencimentos(v2);
        cargoVencimentos.add(cargoVencimento2);

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargoVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(BigDecimal.ZERO.doubleValue(), consolidado.getSalario().doubleValue());
    }

    @Test
    void testCalculoSalarioComDebitoUnico() {
        Pessoa p = construirPessoaGenerica();
        CargoVencimentos cargoVencimento1 = new CargoVencimentos();
        CargoVencimentos cargoVencimento2 = new CargoVencimentos();
        List<CargoVencimentos> cargoVencimentos = new ArrayList<>();

        Cargo cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setId(Integer.valueOf("1"));

        p.setCargo(cargo);

        Vencimentos v1 = new Vencimentos();
        v1.setDescricao("v1");
        v1.setId(Integer.valueOf("1"));
        v1.setTipoVencimento(TipoVencimento.CREDITO);
        v1.setValor(new BigDecimal("5000.00"));

        Vencimentos v2 = new Vencimentos();
        v2.setDescricao("v1");
        v2.setId(Integer.valueOf("1"));
        v2.setTipoVencimento(TipoVencimento.DEBITO);
        v2.setValor(new BigDecimal("1000.00"));

        cargoVencimento1.setCargo(cargo);
        cargoVencimento1.setId(Integer.valueOf("1"));
        cargoVencimento1.setVencimentos(v1);
        cargoVencimentos.add(cargoVencimento1);

        cargoVencimento2.setCargo(cargo);
        cargoVencimento2.setId(Integer.valueOf("1"));
        cargoVencimento2.setVencimentos(v2);
        cargoVencimentos.add(cargoVencimento2);

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargoVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(new BigDecimal("4000.00"), consolidado.getSalario());
    }

    @Test
    void testCalculoSalarioComCreditosEDebitos() {
        Pessoa p = construirPessoaGenerica();
        CargoVencimentos cargoVencimento1 = new CargoVencimentos();
        CargoVencimentos cargoVencimento2 = new CargoVencimentos();
        CargoVencimentos cargoVencimento3 = new CargoVencimentos();
        List<CargoVencimentos> cargoVencimentos = new ArrayList<>();

        Cargo cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setId(Integer.valueOf("1"));

        p.setCargo(cargo);

        Vencimentos v1 = new Vencimentos();
        v1.setDescricao("v1");
        v1.setId(Integer.valueOf("1"));
        v1.setTipoVencimento(TipoVencimento.CREDITO);
        v1.setValor(new BigDecimal("3000.00"));

        Vencimentos v2 = new Vencimentos();
        v2.setDescricao("v1");
        v2.setId(Integer.valueOf("1"));
        v2.setTipoVencimento(TipoVencimento.CREDITO);
        v2.setValor(new BigDecimal("1000.00"));

        Vencimentos v3 = new Vencimentos();
        v3.setDescricao("v1");
        v3.setId(Integer.valueOf("1"));
        v3.setTipoVencimento(TipoVencimento.DEBITO);
        v3.setValor(new BigDecimal("500.00"));

        cargoVencimento1.setCargo(cargo);
        cargoVencimento1.setId(Integer.valueOf("1"));
        cargoVencimento1.setVencimentos(v1);
        cargoVencimentos.add(cargoVencimento1);

        cargoVencimento2.setCargo(cargo);
        cargoVencimento2.setId(Integer.valueOf("1"));
        cargoVencimento2.setVencimentos(v2);
        cargoVencimentos.add(cargoVencimento2);

        cargoVencimento3.setCargo(cargo);
        cargoVencimento3.setId(Integer.valueOf("1"));
        cargoVencimento3.setVencimentos(v3);
        cargoVencimentos.add(cargoVencimento3);

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargoVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(new BigDecimal("3500.00"), consolidado.getSalario());
    }

    @Test
    void testCalculoSalarioComDebitoMaiorQueCredito() {
        Pessoa p = construirPessoaGenerica();
        CargoVencimentos cargoVencimento1 = new CargoVencimentos();
        CargoVencimentos cargoVencimento2 = new CargoVencimentos();
        List<CargoVencimentos> cargoVencimentos = new ArrayList<>();

        Cargo cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setId(Integer.valueOf("1"));

        p.setCargo(cargo);

        Vencimentos v1 = new Vencimentos();
        v1.setDescricao("v1");
        v1.setId(Integer.valueOf("1"));
        v1.setTipoVencimento(TipoVencimento.CREDITO);
        v1.setValor(new BigDecimal("1000.00"));

        Vencimentos v2 = new Vencimentos();
        v2.setDescricao("v1");
        v2.setId(Integer.valueOf("1"));
        v2.setTipoVencimento(TipoVencimento.DEBITO);
        v2.setValor(new BigDecimal("2000.00"));

        cargoVencimento1.setCargo(cargo);
        cargoVencimento1.setId(Integer.valueOf("1"));
        cargoVencimento1.setVencimentos(v1);
        cargoVencimentos.add(cargoVencimento1);

        cargoVencimento2.setCargo(cargo);
        cargoVencimento2.setId(Integer.valueOf("1"));
        cargoVencimento2.setVencimentos(v2);
        cargoVencimentos.add(cargoVencimento2);

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargoVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(new BigDecimal("-1000.00"), consolidado.getSalario());
    }

    @Test
    void testCalculoSalarioSemVencimentos() {
        Pessoa p = construirPessoaGenerica();
        List<CargoVencimentos> cargoVencimentos = new ArrayList<>();

        Cargo cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setId(Integer.valueOf("1"));

        p.setCargo(cargo);

        PessoaSalarioConsolidado consolidado = p.calcularSalarioConsolidado(cargoVencimentos);

        Assertions.assertNotNull(consolidado);
        Assertions.assertEquals(BigDecimal.ZERO.doubleValue(), consolidado.getSalario().doubleValue());
    }
}

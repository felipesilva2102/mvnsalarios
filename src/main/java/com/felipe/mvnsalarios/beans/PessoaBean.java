package com.felipe.mvnsalarios.beans;

import com.felipe.mvnsalarios.domain.Cargo;
import com.felipe.mvnsalarios.domain.Pessoa;
import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import com.felipe.mvnsalarios.service.CargoService;
import com.felipe.mvnsalarios.service.PessoaSalarioConsolidadoService;
import com.felipe.mvnsalarios.service.PessoaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.util.IOUtils;

@Named
@ViewScoped
@Getter
@Setter
@Slf4j
public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PessoaService pessoaService;

    @Inject
    private CargoService cargoService;

    @Inject
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;

    private Pessoa pessoa = new Pessoa();
    private boolean visualizationMode;

    private List<Pessoa> pessoas;
    private Pessoa selectedPessoa;

    private Cargo cargo = new Cargo();
    private String cargoSelecionado;

    @PostConstruct
    public void init() {
        this.pessoas = this.pessoaService.findAll();
    }

    public List<Cargo> getCargos() {
        return cargoService.findAll();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void salvar() {
        try {
            pessoaService.save(pessoa);
            pessoa = new Pessoa();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Operação efetuada!", null));
            this.pessoas = this.pessoaService.findAll();
        } catch (Exception e) {
            log.error("Erro", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro", e.getMessage()));
        }
    }

    public void excluir(Pessoa pessoa) {
        try {
            pessoaService.deleteById(pessoa);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Pessoa removida com sucesso!",
                            null));
            this.pessoas = this.pessoaService.findAll();
        } catch (Exception e) {
            log.error("Erro", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro", e.getMessage()));
        }
    }

    public void prepararEdicao(Pessoa Pessoa) {
        this.pessoa = Pessoa;
    }

    public void prepararDetalhamento(Pessoa Pessoa) {
        this.pessoa = Pessoa;
    }

    public void detailPessoa() {
        visualizationMode = true;
    }

    public void insertUpdatePessoa() {
        visualizationMode = false;
    }

    public void calcularSalarios() {
        pessoaSalarioConsolidadoService.calcularSalarios();
        this.pessoas = this.pessoaService.findAll();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Os salários foram atualizados!",
                        "O cálculo foi concluído com sucesso."));

    }

    public void calcularSalario() {
        pessoaSalarioConsolidadoService.calcularSalario(pessoa);
        this.pessoas = this.pessoaService.findAll();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O salário foi atualizado!",
                        "O cálculo foi concluído com sucesso."));
    }

    public void calcularSalariosAssincrono() {
        pessoaSalarioConsolidadoService.calcularSalariosAssincrono();
        this.pessoas = this.pessoaService.findAll();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Os salários ainda estão sendo calculados!",
                        "Atualize a página no botão 'Atualizar página' após uns 30s."));

    }

    public void deleteSalariosCalculados() {
        pessoaSalarioConsolidadoService.deleteAll();
        this.pessoas = this.pessoaService.findAll();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Os salários foram deletados!", null));
    }

    public void gerarRelatorioSalarios() {
        try {
            InputStream reportStreamJrxml = getClass().getResourceAsStream("/relatorios/relatorioSalarios.jrxml");

            if (reportStreamJrxml == null) {
                throw new RuntimeException("Arquivo do relatório não encontrado!");
            }

            List<PessoaSalarioConsolidado> pessoaSalarioConsolidados = pessoaSalarioConsolidadoService.findAll();

            if (pessoaSalarioConsolidados.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Necessário clicar no botão 'Calcular Salários' ou 'Calcular Salários (Assíncrono)' para calcular salários e poder criar PDF!", null));
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStreamJrxml);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.pessoas);

            Map<String, Object> parametros = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            byte[] conteudoRelatorioVistoria = IOUtils.toByteArray(byteArrayInputStream);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=relatorioSalarios.pdf");
            response.getOutputStream().write(conteudoRelatorioVistoria);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            log.error("Erro na geração do relatório de salários", e);
        }
    }

    public void refresh() {
        this.pessoas = pessoaService.findAll();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Página atualizada",
                        null));
    }
}

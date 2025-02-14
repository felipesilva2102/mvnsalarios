package com.felipe.mvnsalarios.service;

import com.felipe.mvnsalarios.domain.PessoaSalarioConsolidado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Transactional
public class JasperReportService {

    private static final String RELATORIO = "classpath:resources/relatorios/";
    private static final String ARQUIVOJRXML = "relatorioSalarios.jrxml";
    private static final Logger LOGGER = LoggerFactory.getLogger(JasperReportService.class);
    private static final String DESTINPPDF = "C\\:jasper-report\\";

    public void gerarRelatorio(List<PessoaSalarioConsolidado> pessoasSalariosConsolidados) {
        Map<String, Object> params = new HashMap<>();
        String pathGeral = RELATORIO + ARQUIVOJRXML;

        for (PessoaSalarioConsolidado pessoaSalarioConsolidado : pessoasSalariosConsolidados) {
            params.put("nomePessoa", pessoaSalarioConsolidado.getNomePessoa());
            params.put("nomeCargo", pessoaSalarioConsolidado.getNomeCargo());
            params.put("salario", pessoaSalarioConsolidado.getSalario());
        }

        try {
            String folder = getDiretorioSave("relatorio-salarios");
            JasperReport jasperReport = JasperCompileManager.compileReport(pathGeral);
            LOGGER.info("Report compilado: {}", pathGeral);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params);
            LOGGER.info("JasperPrint gerado com sucesso!");
            JasperExportManager.exportReportToPdfFile(jasperPrint, folder);
            LOGGER.info("PDF importado para {}", folder);
        } catch (JRException e) {
            LOGGER.error("Erro ao gerar relatório", e);
            throw new RuntimeException(e);
        }
    }

    private String getDiretorioSave(String relatoriosalarios) {
        this.createDiretorio(relatoriosalarios);
        return DESTINPPDF + relatoriosalarios.concat(".pdf");
    }

    private void createDiretorio(String relatoriosalarios) {
        File dir = new File(relatoriosalarios);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}

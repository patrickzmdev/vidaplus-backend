package instituto.vidaplus.relatorio.service.impl;

import instituto.vidaplus.relatorio.dto.RelatorioFinanceiroDTO;
import instituto.vidaplus.relatorio.dto.ResumoFinanceiroDTO;
import instituto.vidaplus.relatorio.exception.ErroEmRelatorioException;
import instituto.vidaplus.relatorio.model.RelatorioFinanceiro;
import instituto.vidaplus.relatorio.repository.RelatorioRepository;
import instituto.vidaplus.relatorio.service.RelatorioFinanceiroService;
import instituto.vidaplus.utils.validador.FormatadorData;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioFinanceiroServiceImpl implements RelatorioFinanceiroService {

    private final RelatorioRepository relatorioRepository;
    private final FormatadorData formatadorData;
    private static final Logger logger = LoggerFactory.getLogger(RelatorioFinanceiroServiceImpl.class);

    @Override
    public RelatorioFinanceiroDTO registrarRelatorio(RelatorioFinanceiroDTO relatorioDTO) {
        logger.info("Registrando relatório financeiro: {}", relatorioDTO);
        RelatorioFinanceiro relatorio = new RelatorioFinanceiro();
        relatorio.setData(relatorioDTO.getData());
        relatorio.setReceita(relatorioDTO.getReceita());
        relatorio.setDespesa(relatorioDTO.getDespesa());
        relatorio.setLucro(relatorioDTO.getReceita() - relatorioDTO.getDespesa());

        RelatorioFinanceiro salvo = relatorioRepository.save(relatorio);
        logger.debug("Salvo: {}", salvo);
        return new RelatorioFinanceiroDTO(salvo);
    }

    @Override
    public List<RelatorioFinanceiroDTO> buscarRelatorioPorPeriodo(LocalDate inicio, LocalDate fim) {
        return relatorioRepository.findByDataBetweenOrderByDataDesc(inicio, fim)
                .stream()
                .map(RelatorioFinanceiroDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResumoFinanceiroDTO gerarResumoFinanceiro(LocalDate inicio, LocalDate fim) {
        Double receitaTotal = relatorioRepository.calcularReceitaTotal(inicio, fim);
        Double despesaTotal = relatorioRepository.calcularDespesaTotal(inicio, fim);

        ResumoFinanceiroDTO resumo = new ResumoFinanceiroDTO();
        resumo.setReceitaTotal(receitaTotal != null ? receitaTotal : 0.0);
        resumo.setDespesaTotal(despesaTotal != null ? despesaTotal : 0.0);
        resumo.setLucroTotal(resumo.getReceitaTotal() - resumo.getDespesaTotal());
        resumo.setPeriodoInicio(inicio);
        resumo.setPeriodoFim(fim);

        return resumo;
    }

    @Override
    public byte[] exportarRelatorioPDF(LocalDate inicio, LocalDate fim) {
        try {
            logger.info("Exportando relatório PDF de {} a {}", inicio, fim);
            List<RelatorioFinanceiroDTO> relatorios = buscarRelatorioPorPeriodo(inicio, fim);
            ResumoFinanceiroDTO resumo = gerarResumoFinanceiro(inicio, fim);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("periodoInicio", formatadorData.formatarData(inicio));
            parameters.put("periodoFim", formatadorData.formatarData(fim));
            parameters.put("receitaTotal", resumo.getReceitaTotal());
            parameters.put("despesaTotal", resumo.getDespesaTotal());
            parameters.put("lucroTotal", resumo.getLucroTotal());

            InputStream reportStream = getClass().getResourceAsStream("/reports/relatorio_financeiro.jrxml");
            if (reportStream == null) {
                throw new ErroEmRelatorioException("Template de relatório não encontrado: /reports/relatorio_financeiro.jrxml");
            }

            JasperReport jasperReport;
            try {
                InputStream compiledStream = getClass().getResourceAsStream("/reports/relatorio_financeiro.jasper");
                if (compiledStream != null) {
                    jasperReport = (JasperReport) JRLoader.loadObject(compiledStream);
                } else {
                    jasperReport = JasperCompileManager.compileReport(reportStream);
                }
            } catch (JRException e) {
                throw new ErroEmRelatorioException("Erro ao compilar o relatório: " + e.getMessage());
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(relatorios);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            return out.toByteArray();
        } catch (Exception e) {
            throw new ErroEmRelatorioException("Erro ao gerar relatório PDF: " + e.getMessage());
        }
    }

    @Override
    public byte[] exportarRelatorioExcel(LocalDate inicio, LocalDate fim) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            logger.info("Exportando relatório Excel de {} a {}", inicio, fim);

            List<RelatorioFinanceiroDTO> relatorios = buscarRelatorioPorPeriodo(inicio, fim);
            ResumoFinanceiroDTO resumo = gerarResumoFinanceiro(inicio, fim);

            Sheet sheet = workbook.createSheet("Relatório Financeiro");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"Data", "Receita", "Despesa", "Lucro"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (RelatorioFinanceiroDTO relatorio : relatorios) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(formatadorData.formatarData(relatorio.getData()));
                row.createCell(1).setCellValue(relatorio.getReceita());
                row.createCell(2).setCellValue(relatorio.getDespesa());
                row.createCell(3).setCellValue(relatorio.getLucro());
            }

            rowNum += 2;
            Row summaryHeader = sheet.createRow(rowNum++);
            summaryHeader.createCell(0).setCellValue("Resumo Financeiro");

            Row periodoRow = sheet.createRow(rowNum++);
            periodoRow.createCell(0).setCellValue("Período:");
            periodoRow.createCell(1).setCellValue(inicio + " a " + fim);

            Row receitaRow = sheet.createRow(rowNum++);
            receitaRow.createCell(0).setCellValue("Receita Total:");
            receitaRow.createCell(1).setCellValue(resumo.getReceitaTotal());

            Row despesaRow = sheet.createRow(rowNum++);
            despesaRow.createCell(0).setCellValue("Despesa Total:");
            despesaRow.createCell(1).setCellValue(resumo.getDespesaTotal());

            Row lucroRow = sheet.createRow(rowNum++);
            lucroRow.createCell(0).setCellValue("Lucro Total:");
            lucroRow.createCell(1).setCellValue(resumo.getLucroTotal());

            CellStyle numberStyle = workbook.createCellStyle();
            DataFormat numberFormat = workbook.createDataFormat();
            numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));

            for (Row row : sheet) {
                for (int colIndex = 1; colIndex <= 3; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        cell.setCellStyle(numberStyle);
                    }
                }
            }

            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new ErroEmRelatorioException("Erro ao gerar relatório Excel: " + e.getMessage());
        }
    }
}

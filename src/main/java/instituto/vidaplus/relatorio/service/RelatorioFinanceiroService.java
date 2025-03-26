package instituto.vidaplus.relatorio.service;

import instituto.vidaplus.relatorio.dto.RelatorioFinanceiroDTO;
import instituto.vidaplus.relatorio.dto.ResumoFinanceiroDTO;

import java.time.LocalDate;
import java.util.List;

public interface RelatorioFinanceiroService {
    RelatorioFinanceiroDTO registrarRelatorio(RelatorioFinanceiroDTO relatorioDTO);
    List<RelatorioFinanceiroDTO> buscarRelatorioPorPeriodo(LocalDate inicio, LocalDate fim);
    ResumoFinanceiroDTO gerarResumoFinanceiro(LocalDate inicio, LocalDate fim);
    byte[] exportarRelatorioPDF(LocalDate inicio, LocalDate fim);
    byte[] exportarRelatorioExcel(LocalDate inicio, LocalDate fim);
}

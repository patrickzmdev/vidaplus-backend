package instituto.vidaplus.relatorio.repository;

import instituto.vidaplus.relatorio.model.RelatorioFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RelatorioRepository extends JpaRepository<RelatorioFinanceiro, Long> {
    List<RelatorioFinanceiro> findByDataBetweenOrderByDataDesc(LocalDate inicio, LocalDate fim);

    @Query("SELECT SUM(r.receita) FROM RelatorioFinanceiro r WHERE r.data BETWEEN :inicio AND :fim")
    Double calcularReceitaTotal(LocalDate inicio, LocalDate fim);

    @Query("SELECT SUM(r.despesa) FROM RelatorioFinanceiro r WHERE r.data BETWEEN :inicio AND :fim")
    Double calcularDespesaTotal(LocalDate inicio, LocalDate fim);
}

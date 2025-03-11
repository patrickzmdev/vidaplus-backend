package instituto.vidaplus.relatorio.repository;

import instituto.vidaplus.relatorio.model.RelatorioFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatorioRepository extends JpaRepository<RelatorioFinanceiro, Long> {
}

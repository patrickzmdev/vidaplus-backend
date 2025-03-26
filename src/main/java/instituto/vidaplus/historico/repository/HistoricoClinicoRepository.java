package instituto.vidaplus.historico.repository;

import instituto.vidaplus.historico.enums.TipoHistoricoEnum;
import instituto.vidaplus.historico.model.HistoricoClinico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoClinicoRepository extends JpaRepository<HistoricoClinico, Long> {
    Page<HistoricoClinico> findByPacienteIdOrderByDataRegistroDesc(Long pacienteId, Pageable pageable);
    Page<HistoricoClinico> findByPacienteIdAndTipoOrderByDataRegistroDesc(Long pacienteId, TipoHistoricoEnum tipoHistoricoEnum, Pageable pageable);
}

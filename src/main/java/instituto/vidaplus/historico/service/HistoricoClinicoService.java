package instituto.vidaplus.historico.service;

import instituto.vidaplus.historico.dto.HistoricoClinicoDTO;
import instituto.vidaplus.historico.enums.TipoHistoricoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoricoClinicoService {
    HistoricoClinicoDTO registrarHistorico(Long pacienteId, HistoricoClinicoDTO historicoClinicoDTO);
    Page<HistoricoClinicoDTO> buscarHistoricoPorPaciente(Long pacienteId, Pageable pageable);
    Page<HistoricoClinicoDTO> buscarHistoricoPorPacienteETipo(Long pacienteId, TipoHistoricoEnum tipoHistoricoEnum, Pageable pageable);
}

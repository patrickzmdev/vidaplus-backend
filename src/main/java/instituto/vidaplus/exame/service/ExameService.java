package instituto.vidaplus.exame.service;

import instituto.vidaplus.exame.dto.ExameDTO;
import instituto.vidaplus.exame.dto.ExameSuprimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExameService {
    ExameDTO agendarExame(Long administradorId,Long pacienteId, ExameDTO exameDTO);
    ExameDTO buscarExame(Long id);
    Page<ExameDTO> buscarExamesPorPaciente(Long pacienteId, Pageable pageable);
    String finalizarExame(Long id);
    String cancelarExame(Long id);
    ExameSuprimentoDTO adicionarSuprimentoAExame(Long exameId, Long suprimentoId, Integer quantidadeUtilizada);
}

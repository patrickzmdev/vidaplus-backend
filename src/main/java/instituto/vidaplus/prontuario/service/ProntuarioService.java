package instituto.vidaplus.prontuario.service;

import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ProntuarioService {
    ProntuarioDTO criarProntuario(Long consultaId, ProntuarioDTO prontuarioDTO);
    ProntuarioDTO buscarProntuarioPorId(Long prontuarioId);
    ProntuarioDTO atualizarProntuario(Long prontuarioId, ProntuarioDTO prontuarioDTO);
    Page<ProntuarioDTO> buscarProntuariosPorPacienteId(Long pacienteId, Pageable pageable);
    Page<ProntuarioDTO> buscarProntuariosPorProfissionalId(Long profissionalId, Pageable pageable);
    ProntuarioDTO buscarProntuarioPorConsultaId(Long consultaId);
    Page<ProntuarioDTO> buscarProntuariosPorPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
    byte[] gerarRelatorio(Long prontuarioId);
}

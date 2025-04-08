package instituto.vidaplus.receita.service;

import instituto.vidaplus.receita.dto.ReceitaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ReceitaService {
    ReceitaDTO criarReceita(Long consultaId);
    ReceitaDTO buscarReceitaPorId(Long receitaId);
    Page<ReceitaDTO> buscarReceitasPorPacienteId(Long pacienteId, Pageable pageable);
    Page<ReceitaDTO> buscarReceitasPorProfissionalId(Long profissionalId, Pageable pageable);
    Page<ReceitaDTO> buscarReceitaPorConsultaId(Long consultaId, Pageable pageable);
    Page<ReceitaDTO> buscarReceitasPorPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
    byte[] gerarReceita(Long receitaId);
}

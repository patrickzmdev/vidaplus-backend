package instituto.vidaplus.consulta.service;

import instituto.vidaplus.consulta.dto.ConsultaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsultaService {
    ConsultaDTO criarConsulta(Long pacienteId, Long profissionalId, Long agendaId, ConsultaDTO consultaDTO);
    ConsultaDTO buscarConsultaPorId(Long id);
    Page<ConsultaDTO> listarConsultas(Pageable pageable);
    String deletarConsulta(Long id);
    Page<ConsultaDTO> buscarConsultasPorPaciente(Long pacienteId, Pageable pageable);
    Page<ConsultaDTO> buscarConsultasPorProfissional(Long profissionalId, Pageable pageable);
    ConsultaDTO confirmarConsulta(Long id);
    ConsultaDTO cancelarConsulta(Long id);

}

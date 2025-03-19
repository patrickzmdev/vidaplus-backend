package instituto.vidaplus.consulta.service;

import instituto.vidaplus.consulta.dto.ConsultaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsultaService {
    ConsultaDTO criarConsulta(ConsultaDTO consultaDTO);
    ConsultaDTO buscarConsultaPorId(Long id);
    Page<ConsultaDTO> listarConsultas(Pageable pageable);
    ConsultaDTO atualizarConsulta(Long id, ConsultaDTO consultaDTO);
    String deletarConsulta(Long id);
    ConsultaDTO converterParaTelemedicina(Long consultaId, String linkVideoChamada);
    ConsultaDTO removerTelemedicina(Long consultaId);
    ConsultaDTO atualizarLinkTelemedicina(Long consultaId, String novoLink);
    Boolean isConsultaTelemedicina(Long consultaId);
    List<ConsultaDTO> buscarConsultasPorPaciente(Long pacienteId);
    List<ConsultaDTO> buscarConsultasPorProfissional(Long profissionalId);
}

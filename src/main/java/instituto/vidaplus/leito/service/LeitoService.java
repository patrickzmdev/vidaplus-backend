package instituto.vidaplus.leito.service;

import instituto.vidaplus.leito.dto.LeitoDTO;
import instituto.vidaplus.paciente.dto.PacienteDTO;
import instituto.vidaplus.paciente.dto.PacienteResumidoDTO;
import org.springframework.data.domain.Page;

public interface LeitoService {
    LeitoDTO adicionarLeito(Long unidadeHospitalarId, LeitoDTO leitoDTO);
    LeitoDTO buscarLeito(Long id);
    String deletarLeito(Long id);
    Boolean verificarLeitoDisponivel(Long id);
    PacienteResumidoDTO buscarPacienteInternadoPorLeito(Long leitoId, Long unidadeHospitalarId);
    Page<LeitoDTO> listarLeitosDisponiveis(Long unidadeHospitalarId);
}

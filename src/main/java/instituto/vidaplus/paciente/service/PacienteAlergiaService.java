package instituto.vidaplus.paciente.service;

import instituto.vidaplus.paciente.dto.PacienteAlergiaDTO;
import instituto.vidaplus.paciente.enums.AlergiaEnum;

import java.util.List;

public interface PacienteAlergiaService {
    PacienteAlergiaDTO adicionarAlergia(Long pacienteId, AlergiaEnum alergia, String observacao);
    List<PacienteAlergiaDTO> listaAlergiasPaciente(Long pacienteId, PacienteAlergiaDTO pacienteAlergia);
    String excluirAlergia(Long pacienteId, AlergiaEnum alergia);
}

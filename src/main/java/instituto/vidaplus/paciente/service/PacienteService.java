package instituto.vidaplus.paciente.service;

import instituto.vidaplus.paciente.dto.PacienteDTO;
import instituto.vidaplus.paciente.model.Paciente;

public interface PacienteService {
    PacienteDTO cadastrarPaciente(Long administradorId, PacienteDTO paciente);
    PacienteDTO editarPaciente(Long pacienteId, PacienteDTO paciente);
    PacienteDTO buscarPaciente(Long pacienteId);
    String excluirPaciente(Long pacienteId);
}

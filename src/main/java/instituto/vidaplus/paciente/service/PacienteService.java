package instituto.vidaplus.paciente.service;

import instituto.vidaplus.paciente.dto.PacienteDTO;
import instituto.vidaplus.paciente.model.Paciente;

public interface PacienteService {
    PacienteDTO cadastrarPaciente(Long administradorId, PacienteDTO paciente);
    PacienteDTO editarPaciente(Long pacienteId, PacienteDTO paciente);
    Paciente buscarPaciente(Long pacienteId);
    String excluirPaciente(Long pacienteId);
}

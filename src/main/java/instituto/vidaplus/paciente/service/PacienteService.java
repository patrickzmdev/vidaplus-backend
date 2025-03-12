package instituto.vidaplus.paciente.service;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.prontuario.model.Prontuario;

import java.util.List;

public interface PacienteService {
    Paciente cadastrarPaciente(Paciente paciente);
    String editarPaciente(Long pacienteId, Paciente paciente);
    Paciente buscarPaciente(Long pacienteId);
    void excluirPaciente(Long pacienteId);
}

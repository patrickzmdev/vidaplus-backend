package instituto.vidaplus.paciente.service;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.prontuario.model.Prontuario;

import java.util.List;

public interface PacienteService {
    Paciente cadastrarPaciente(Paciente paciente);
    Long editarPaciente(Long pacienteId, Paciente paciente);
    void excluirPaciente(Long pacienteId);
    Paciente visualizarHistoricoClinico(Long pacienteId);
    Consulta agendarConsulta(Long pacienteId, Consulta consulta);
    void cancelarConsulta(Long consultaId);
    List<Consulta> listarConsultas(Long pacienteId);
    List<Exame> listarExames(Long pacienteId);
    List<Prontuario> listarProntuarios(Long pacienteId);
    void receberNotificacoes(Long pacienteId, String mensagem);
    void acessarTeleconsulta(Long pacienteId);
}

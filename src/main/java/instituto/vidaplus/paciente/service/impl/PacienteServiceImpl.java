package instituto.vidaplus.paciente.service.impl;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.consulta.repository.ConsultaRepository;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.exame.repository.ExameRepository;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.paciente.service.PacienteService;
import instituto.vidaplus.prontuario.model.Prontuario;
import instituto.vidaplus.prontuario.repository.ProntuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final ExameRepository exameRepository;
    private final ProntuarioRepository prontuarioRepository;

    @Override
    @Transactional
    public Paciente cadastrarPaciente(Paciente paciente) {
        return null;
    }

    @Override
    public Long editarPaciente(Long pacienteId, Paciente paciente) {
        return 0L;
    }

    @Override
    public void excluirPaciente(Long pacienteId) {

    }

    @Override
    public Paciente visualizarHistoricoClinico(Long pacienteId) {
        return null;
    }

    @Override
    public Consulta agendarConsulta(Long pacienteId, Consulta consulta) {
        return null;
    }

    @Override
    public void cancelarConsulta(Long consultaId) {

    }

    @Override
    public List<Consulta> listarConsultas(Long pacienteId) {
        return List.of();
    }

    @Override
    public List<Exame> listarExames(Long pacienteId) {
        return List.of();
    }

    @Override
    public List<Prontuario> listarProntuarios(Long pacienteId) {
        return List.of();
    }

    @Override
    public void receberNotificacoes(Long pacienteId, String mensagem) {

    }

    @Override
    public void acessarTeleconsulta(Long pacienteId) {

    }
}

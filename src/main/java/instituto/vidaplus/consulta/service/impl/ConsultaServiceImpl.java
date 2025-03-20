package instituto.vidaplus.consulta.service.impl;

import instituto.vidaplus.agenda.exception.AgendaNaoEncontradaException;
import instituto.vidaplus.agenda.model.Agenda;
import instituto.vidaplus.agenda.repository.AgendaRepository;
import instituto.vidaplus.consulta.dto.ConsultaDTO;
import instituto.vidaplus.consulta.exception.ConsultaNaoEncontradaException;
import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.consulta.repository.ConsultaRepository;
import instituto.vidaplus.consulta.service.ConsultaService;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.profissional.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final AgendaRepository agendaRepository;

    @Override
    @Transactional
    public ConsultaDTO criarConsulta(ConsultaDTO consultaDTO) {
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        Profissional profissional = profissionalRepository.findById(consultaDTO.getProfissionalId())
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));

        Agenda agenda = agendaRepository.findById(consultaDTO.getAgendaId())
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);
        consulta.setAgenda(agenda);
        consulta.setDataHoraInicio(consultaDTO.getDataHoraInicio());
        consulta.setDataHoraFim(consultaDTO.getDataHoraFim());
        consulta.setStatus(consultaDTO.getStatus());
        consulta.setMotivoConsulta(consultaDTO.getMotivoConsulta());

        Consulta consultaSalva = consultaRepository.save(consulta);
        return new ConsultaDTO(consultaSalva);
    }

    @Override
    public ConsultaDTO buscarConsultaPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
        return new ConsultaDTO(consulta);
    }

    @Override
    public Page<ConsultaDTO> listarConsultas(Pageable pageable) {
        return consultaRepository.findAll(pageable).map(ConsultaDTO::new);
    }

    @Override
    @Transactional
    public ConsultaDTO atualizarConsulta(Long id, ConsultaDTO consultaDTO) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));

        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        Profissional profissional = profissionalRepository.findById(consultaDTO.getProfissionalId())
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));

        Agenda agenda = agendaRepository.findById(consultaDTO.getAgendaId())
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));

        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);
        consulta.setAgenda(agenda);
        consulta.setDataHoraInicio(consultaDTO.getDataHoraInicio());
        consulta.setDataHoraFim(consultaDTO.getDataHoraFim());
        consulta.setStatus(consultaDTO.getStatus());
        consulta.setMotivoConsulta(consultaDTO.getMotivoConsulta());
        Consulta consultaSalva = consultaRepository.save(consulta);
        return new ConsultaDTO(consultaSalva);
    }

    @Override
    @Transactional
    public String deletarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
        consultaRepository.delete(consulta);
        return "Consulta deletada com sucesso";
    }

    @Override
    public Page<ConsultaDTO> buscarConsultasPorPaciente(Long pacienteId, Pageable pageable) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
        Page<Consulta> consultas = consultaRepository.findByPacienteId(pacienteId, pageable);
        return consultas.map(ConsultaDTO::new);
    }

    @Override
    public Page<ConsultaDTO> buscarConsultasPorProfissional(Long profissionalId, Pageable pageable) {
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));
        Page<Consulta> consultas = consultaRepository.findByProfissionalId(profissionalId, pageable);
        return consultas.map(ConsultaDTO::new);
    }
}

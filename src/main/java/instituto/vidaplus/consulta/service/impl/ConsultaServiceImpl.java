package instituto.vidaplus.consulta.service.impl;

import instituto.vidaplus.agenda.exception.AgendaNaoEncontradaException;
import instituto.vidaplus.agenda.model.Agenda;
import instituto.vidaplus.agenda.repository.AgendaRepository;
import instituto.vidaplus.consulta.dto.ConsultaDTO;
import instituto.vidaplus.consulta.enums.StatusConsultaEnum;
import instituto.vidaplus.consulta.exception.ConsultaNaoEncontradaException;
import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.consulta.repository.ConsultaRepository;
import instituto.vidaplus.consulta.service.ConsultaService;
import instituto.vidaplus.email.service.EmailService;
import instituto.vidaplus.exception.genericas.DataInvalidaException;
import instituto.vidaplus.horario.exception.ConflitoDeHorarioException;
import instituto.vidaplus.horario.exception.HorarioNaoEncontradoException;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.profissional.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final AgendaRepository agendaRepository;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(ConsultaServiceImpl.class);

    @Override
    @Transactional
    public ConsultaDTO criarConsulta(Long pacienteId, Long agendaId, ConsultaDTO consultaDTO) {
        logger.info("Criando consulta: {}", consultaDTO);
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));

        LocalDate dataConsulta = consultaDTO.getData();
        LocalTime horaInicio = consultaDTO.getHoraInicio();
        LocalTime horaFim = consultaDTO.getHoraFim();

        if(dataConsulta.isBefore(LocalDate.now())) {
            throw new DataInvalidaException("A data da consulta não pode ser anterior a data atual");
        }

        int dayOfWeekValue = dataConsulta.getDayOfWeek().getValue();

        boolean horarioDisponivel = agenda.getHorariosDisponiveis().stream()
                .anyMatch(horario ->
                        horario.getDiaDaSemana().getValor() == dayOfWeekValue &&
                                horario.getDisponivel() &&
                                !horaInicio.isBefore(horario.getHoraInicio()) &&
                                !horaFim.isAfter(horario.getHoraFim())
                );

        if (!horarioDisponivel) {
            throw new HorarioNaoEncontradoException("Não há horário disponível para esta data e hora na agenda do profissional");
        }

        boolean horarioOcupado = consultaRepository.existsByAgendaIdAndDataAndHoraInicioLessThanEqualAndHoraFimGreaterThanEqual(
                agendaId, dataConsulta, horaFim, horaInicio);

        if (horarioOcupado) {
            throw new ConflitoDeHorarioException("Este horário já está ocupado por outra consulta");
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setProfissional(agenda.getProfissional());
        consulta.setAgenda(agenda);
        consulta.setData(consultaDTO.getData());
        consulta.setHoraInicio(consultaDTO.getHoraInicio());
        consulta.setHoraFim(consultaDTO.getHoraFim());
        consulta.setStatus(StatusConsultaEnum.AGENDADA);
        consulta.setMotivoConsulta(consultaDTO.getMotivoConsulta());
        emailService.confirmacaoConsulta(paciente, "Confirmação de Consulta", consulta);
        Consulta consultaSalva = consultaRepository.save(consulta);
        logger.debug("Consulta salva: {}", consultaSalva);
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
    public String deletarConsulta(Long id) {
        logger.info("Deletando consulta: {}", id);
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
        consultaRepository.delete(consulta);
        logger.debug("Consulta deletada: {}", consulta);
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

    @Override
    public ConsultaDTO confirmarConsulta(Long id) {
        logger.info("Confirmando consulta: {}", id);
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
        consulta.setStatus(StatusConsultaEnum.CONFIRMADA);
        Consulta consultaSalva = consultaRepository.save(consulta);
        logger.debug("Consulta salva: {}", consultaSalva);
        return new ConsultaDTO(consultaSalva);
    }

    @Override
    public ConsultaDTO cancelarConsulta(Long id) {
        logger.info("Cancelando consulta: {}", id);
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
        consulta.setStatus(StatusConsultaEnum.CANCELADA);
        Consulta consultaSalva = consultaRepository.save(consulta);
        logger.debug("Consulta salva: {}", consultaSalva);
        return new ConsultaDTO(consultaSalva);
    }
}

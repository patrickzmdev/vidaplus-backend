package instituto.vidaplus.agenda.service;

import instituto.vidaplus.agenda.dto.AgendaDTO;
import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AgendaService {
    AgendaDTO criarAgenda(AgendaDTO agendaDTO);
    AgendaDTO buscarAgendaPorId(Long id);
    Page<AgendaDTO> listarAgendas(Pageable pageable);
    AgendaDTO atualizarAgenda(Long id, AgendaDTO agendaDTO);
    String deletarAgenda(Long id);
    AgendaDTO buscarAgendaPorProfissional(Long profissionalId);
    AgendaDTO ativarAgenda(Long id);
    AgendaDTO desativarAgenda(Long id);
    AgendaDTO adicionarHorarioDisponivel(Long agendaId, HorarioDisponivelDTO horarioDTO);
    AgendaDTO removerHorarioDisponivel(Long agendaId, Long horarioId);
    List<HorarioDisponivelDTO> listarHorariosDisponiveisPorAgenda(Long agendaId);
    Boolean verificarDisponibilidadeHorario(Long agendaId, LocalDate data, LocalTime horaInicio, LocalTime horaFim);
    List<HorarioDisponivelDTO> buscarHorariosDisponiveisPorData(Long agendaId, LocalDate data);
}

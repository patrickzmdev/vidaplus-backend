package instituto.vidaplus.agenda.service;

import instituto.vidaplus.agenda.dto.AgendaDTO;
import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AgendaService {
    AgendaDTO criarAgenda(Long profissionalId, AgendaDTO agendaDTO);
    AgendaDTO buscarAgendaPorId(Long id);
    Page<AgendaDTO> listarAgendas(Pageable pageable);
    AgendaDTO atualizarAgenda(Long id, AgendaDTO agendaDTO);
    String deletarAgenda(Long id);
    AgendaDTO buscarAgendaPorProfissional(Long profissionalId);
    AgendaDTO ativarAgenda(Long id);
    AgendaDTO desativarAgenda(Long id);
}

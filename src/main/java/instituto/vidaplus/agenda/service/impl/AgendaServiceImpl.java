package instituto.vidaplus.agenda.service.impl;

import instituto.vidaplus.agenda.dto.AgendaDTO;
import instituto.vidaplus.agenda.repository.AgendaRepository;
import instituto.vidaplus.agenda.service.AgendaService;
import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;

    @Override
    public AgendaDTO criarAgenda(AgendaDTO agendaDTO) {
        return null;
    }

    @Override
    public AgendaDTO buscarAgendaPorId(Long id) {
        return null;
    }

    @Override
    public Page<AgendaDTO> listarAgendas(Pageable pageable) {
        return null;
    }

    @Override
    public AgendaDTO atualizarAgenda(Long id, AgendaDTO agendaDTO) {
        return null;
    }

    @Override
    public String deletarAgenda(Long id) {
        return "";
    }

    @Override
    public AgendaDTO buscarAgendaPorProfissional(Long profissionalId) {
        return null;
    }

    @Override
    public AgendaDTO ativarAgenda(Long id) {
        return null;
    }

    @Override
    public AgendaDTO desativarAgenda(Long id) {
        return null;
    }

    @Override
    public AgendaDTO adicionarHorarioDisponivel(Long agendaId, HorarioDisponivelDTO horarioDTO) {
        return null;
    }

    @Override
    public AgendaDTO removerHorarioDisponivel(Long agendaId, Long horarioId) {
        return null;
    }

    @Override
    public List<HorarioDisponivelDTO> listarHorariosDisponiveisPorAgenda(Long agendaId) {
        return List.of();
    }

    @Override
    public Boolean verificarDisponibilidadeHorario(Long agendaId, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        return null;
    }

    @Override
    public List<HorarioDisponivelDTO> buscarHorariosDisponiveisPorData(Long agendaId, LocalDate data) {
        return List.of();
    }
}

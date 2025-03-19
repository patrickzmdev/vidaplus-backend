package instituto.vidaplus.horario.service;

import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HorarioDisponivelService {
    HorarioDisponivelDTO criarHorarioDisponivel(HorarioDisponivelDTO horarioDisponivelDTO);
    HorarioDisponivelDTO buscarHorarioDisponivelPorId(Long id);
    Page<HorarioDisponivelDTO> listarHorarios(Pageable pageable);
    HorarioDisponivelDTO atualizarHorarioDisponivel(Long id, HorarioDisponivelDTO horarioDisponivelDTO);
    String deletarHorarioDisponivel(Long id);
    List<HorarioDisponivelDTO> listarHorariosPorAgenda(Long agendaId);
    List<HorarioDisponivelDTO> listarHorariosDisponiveisPorAgenda(Long agendaId);
    String marcarHorarioComoIndisponivel(Long id);
    String marcarHorarioComoDisponivel(Long id);

}

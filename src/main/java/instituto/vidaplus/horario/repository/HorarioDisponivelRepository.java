package instituto.vidaplus.horario.repository;

import instituto.vidaplus.horario.enums.DiasDaSemanaEnum;
import instituto.vidaplus.horario.model.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
    List<HorarioDisponivel> findByAgendaId(Long id);
    List<HorarioDisponivel> findByAgendaIdAndDisponivelTrue(Long id);
    List<HorarioDisponivel> findByAgendaIdAndDiaDaSemanaAndDisponivelTrue(Long id, DiasDaSemanaEnum diaDaSemana);
    Boolean existsByAgendaIdAndDiaDaSemanaAndHoraInicioAndHoraFim(Long agendaId, DiasDaSemanaEnum diaDaSemana, LocalTime horaInicio, LocalTime horaFim);
}

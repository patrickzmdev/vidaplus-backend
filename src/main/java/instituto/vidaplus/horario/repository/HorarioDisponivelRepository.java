package instituto.vidaplus.horario.repository;

import instituto.vidaplus.horario.model.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
    List<HorarioDisponivel> findByAgendaId(Long id);
    List<HorarioDisponivel> findByAgendaIdAndDisponivelTrue(Long id);
}

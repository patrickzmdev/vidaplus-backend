package instituto.vidaplus.agenda.repository;

import instituto.vidaplus.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}

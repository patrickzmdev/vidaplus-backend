package instituto.vidaplus.agenda.repository;

import instituto.vidaplus.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    Optional<Agenda> findByProfissionalId(Long id);
}

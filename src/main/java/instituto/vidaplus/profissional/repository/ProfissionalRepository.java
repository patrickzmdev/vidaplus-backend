package instituto.vidaplus.profissional.repository;

import instituto.vidaplus.profissional.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
}

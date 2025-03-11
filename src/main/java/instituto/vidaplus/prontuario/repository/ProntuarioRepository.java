package instituto.vidaplus.prontuario.repository;

import instituto.vidaplus.prontuario.model.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
}

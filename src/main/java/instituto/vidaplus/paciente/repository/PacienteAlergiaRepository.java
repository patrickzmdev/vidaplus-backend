package instituto.vidaplus.paciente.repository;

import instituto.vidaplus.paciente.model.PacienteAlergia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteAlergiaRepository extends JpaRepository<PacienteAlergia, Long> {
    boolean existsByPacienteId(Long pacienteId);
}

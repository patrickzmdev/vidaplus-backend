package instituto.vidaplus.paciente.repository;

import instituto.vidaplus.paciente.enums.AlergiaEnum;
import instituto.vidaplus.paciente.model.PacienteAlergia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteAlergiaRepository extends JpaRepository<PacienteAlergia, Long> {
    boolean existsByPacienteId(Long pacienteId);

    List<PacienteAlergia> findByPacienteId(Long pacienteId);
    PacienteAlergia findByPacienteIdAndAlergia(Long pacienteId, AlergiaEnum alergia);
}

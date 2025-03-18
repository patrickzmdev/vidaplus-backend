package instituto.vidaplus.exame.repository;

import instituto.vidaplus.exame.model.Exame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExameRepository extends JpaRepository<Exame, Long> {
    Page<Exame> findByPacienteId(Long pacienteId, Pageable pageable);
}

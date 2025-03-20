package instituto.vidaplus.consulta.repository;

import instituto.vidaplus.consulta.model.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Page<Consulta> findByPacienteId(Long pacienteId, Pageable pageable);
    Page<Consulta> findByProfissionalId(Long profissionalId, Pageable pageable);
}

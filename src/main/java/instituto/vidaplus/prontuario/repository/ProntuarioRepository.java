package instituto.vidaplus.prontuario.repository;

import instituto.vidaplus.prontuario.model.Prontuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    Page<Prontuario> findByPacienteId(Long pacienteId, Pageable pageable);
    Page<Prontuario> findByProfissionalId(Long profissionalId, Pageable pageable);
    Prontuario findByConsultaId(Long consultaId);
    Page<Prontuario> findByPacienteIdAndDataRegistroBetween(Long pacienteId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
}

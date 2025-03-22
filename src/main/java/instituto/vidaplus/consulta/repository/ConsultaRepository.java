package instituto.vidaplus.consulta.repository;

import instituto.vidaplus.consulta.model.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

import java.time.LocalTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Page<Consulta> findByPacienteId(Long pacienteId, Pageable pageable);
    Page<Consulta> findByProfissionalId(Long profissionalId, Pageable pageable);
    Boolean existsByAgendaIdAndDataAndHoraInicioLessThanEqualAndHoraFimGreaterThanEqual(
            Long agendaId, LocalDate data, LocalTime HoraInicio, LocalTime HoraFim);
}

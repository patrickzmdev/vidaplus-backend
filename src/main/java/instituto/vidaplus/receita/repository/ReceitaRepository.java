package instituto.vidaplus.receita.repository;

import instituto.vidaplus.receita.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    Page<Receita> findReceitasByPacienteId(Long pacienteId, Pageable pageable);
    Page<Receita> findReceitasByProfissionalId(Long profissionalId, Pageable pageable);
    Page<Receita> findReceitaByConsultaId(Long consultaId, Pageable pageable);
    Page<Receita> findByPacienteIdAndDataEmissaoBetween(Long pacienteId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
}

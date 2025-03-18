package instituto.vidaplus.internacao.repository;

import instituto.vidaplus.internacao.model.Internacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternacaoRepository extends JpaRepository<Internacao, Long> {
    Optional<Internacao> findByLeitoIdAndAtivaTrue(Long leitoId);
    Boolean existsByPacienteIdAndAtivaTrue(Long pacienteId);
}

package instituto.vidaplus.internacao.repository;

import instituto.vidaplus.internacao.model.Internacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternacaoRepository extends JpaRepository<Internacao, Long> {
}

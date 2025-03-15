package instituto.vidaplus.suprimento.repository;

import instituto.vidaplus.suprimento.model.Suprimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuprimentoRepository extends JpaRepository<Suprimento, Long> {
    Page<Suprimento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}

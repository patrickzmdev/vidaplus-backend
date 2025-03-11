package instituto.vidaplus.receita.repository;

import instituto.vidaplus.receita.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}

package instituto.vidaplus.telemedicina.repository;

import instituto.vidaplus.telemedicina.model.Telemedicina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelemedicinaRepository extends JpaRepository<Telemedicina, Long> {
    Boolean existsByConsultaId(Long consultaId);
    Optional<Telemedicina> findByConsultaId(Long consultaId);
}

package instituto.vidaplus.leito.repository;

import instituto.vidaplus.leito.model.Leito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeitoRepository extends JpaRepository<Leito, Long> {
    Page<Leito> findByUnidadeHospitalarIdAndOcupadoFalse(Long unidadeHospitalarId, Pageable pageable);
    Boolean existsByNumeroAndUnidadeHospitalarId(Integer numero, Long unidadeHospitalarId);
}

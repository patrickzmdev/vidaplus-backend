package instituto.vidaplus.unidade.repository;

import instituto.vidaplus.unidade.dto.UnidadeHospitalarDTO;
import instituto.vidaplus.unidade.model.UnidadeHospitalar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeHospitalarRepository extends JpaRepository<UnidadeHospitalar, Long> {
    Page<UnidadeHospitalar> findByCidadeContaining(String cidade, Pageable pageable);
}

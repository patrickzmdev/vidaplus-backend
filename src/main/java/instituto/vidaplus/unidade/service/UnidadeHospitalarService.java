package instituto.vidaplus.unidade.service;

import instituto.vidaplus.unidade.dto.UnidadeHospitalarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnidadeHospitalarService {
    UnidadeHospitalarDTO cadastrarUnidadeHospitalar(Long administradorId, UnidadeHospitalarDTO unidadeHospitalarDTO);
    UnidadeHospitalarDTO editarUnidadeHospitalar(Long id, UnidadeHospitalarDTO unidadeHospitalarDTO);
    String excluirUnidadeHospitalar(Long id);
    UnidadeHospitalarDTO buscarUnidadeHospitalar(Long id);
    Page<UnidadeHospitalarDTO> buscarUnidadeHospitalarPorCidade(String cidade, Pageable pageable);
}

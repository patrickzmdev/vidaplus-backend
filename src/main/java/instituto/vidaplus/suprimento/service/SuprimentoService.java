package instituto.vidaplus.suprimento.service;

import instituto.vidaplus.suprimento.dto.SuprimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SuprimentoService {
    SuprimentoDTO cadastrarSuprimento(SuprimentoDTO suprimentoDTO);
    SuprimentoDTO editarSuprimento(Long id, SuprimentoDTO suprimentoDTO);
    String excluirSuprimento(Long id);
    SuprimentoDTO buscarSuprimento(Long id);
    Page<SuprimentoDTO> listarSuprimentos(Pageable pageable);
    Page<SuprimentoDTO> buscarSuprimentosPorNome(String nome, Pageable pageable);
}

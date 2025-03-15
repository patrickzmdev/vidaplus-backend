package instituto.vidaplus.profissional.service;

import instituto.vidaplus.profissional.dto.ProfissionalDTO;
import instituto.vidaplus.profissional.dto.ProfissionalResumoDTO;
import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfissionalService {
    ProfissionalDTO cadastrarProfissional(Long administradorId, ProfissionalDTO profissionalDTO);
    ProfissionalDTO editarProfissional(Long id, ProfissionalDTO profissionalDTO);
    String excluirProfissional(Long id);
    ProfissionalDTO buscarProfissional(Long id);
    Page<ProfissionalResumoDTO> buscarProfissionaisPorNome(String nome, Pageable pageable);
    Page<ProfissionalResumoDTO> buscarProfissionaisPorEspecialidade(EspecialidadeEnum especialidade, Pageable pageable);
    Page<ProfissionalResumoDTO> buscarProfissionaisPorCidade(String cidade, Pageable pageable);
    Page<ProfissionalResumoDTO> buscarProfissionaisPorEspecialidadeCidade(EspecialidadeEnum especialidade, String cidade, Pageable pageable);
}

package instituto.vidaplus.profissional.repository;

import instituto.vidaplus.profissional.dto.ProfissionalResumoDTO;
import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfissionalCustomRepository {
    Page<ProfissionalResumoDTO> findProfissionaisByNomeContaining(String nome, Pageable pageable);
    Page<ProfissionalResumoDTO> findProfissionaisByEspecialidade(EspecialidadeEnum especialidade, Pageable pageable);
    Page<ProfissionalResumoDTO> findProfissionaisByCidadeContaining(String cidade, Pageable pageable);
    Page<ProfissionalResumoDTO> findProfissionaisByEspecialidadeAndCidadeContaining(
            EspecialidadeEnum especialidade, String cidade, Pageable pageable);
}

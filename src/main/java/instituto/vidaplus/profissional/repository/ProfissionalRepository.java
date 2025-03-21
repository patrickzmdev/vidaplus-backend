package instituto.vidaplus.profissional.repository;

import instituto.vidaplus.profissional.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
    Boolean existsByTelefone(String telefone);
    Boolean existsByRegistro(String registro);
    Boolean existsByCpfAndIdNot(String cpf, Long id);
    Boolean existsByEmailAndIdNot(String email, Long id);
    Boolean existsByTelefoneAndIdNot(String telefone, Long id);
    Boolean existsByRegistroAndIdNot(String registro, Long id);
}

package instituto.vidaplus.paciente.repository;

import instituto.vidaplus.paciente.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
    Boolean existsByTelefone(String telefone);
}

package instituto.vidaplus.seguranca.repository;

import instituto.vidaplus.seguranca.enums.UserRoles;
import instituto.vidaplus.seguranca.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(String nome);
}

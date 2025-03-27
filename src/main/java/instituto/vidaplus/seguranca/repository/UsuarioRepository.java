package instituto.vidaplus.seguranca.repository;

import instituto.vidaplus.seguranca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
    Optional<Usuario> findByEmail(String email);
    boolean existsByNomeUsuario(String nomeUsuario);
    boolean existsByEmail(String email);
}

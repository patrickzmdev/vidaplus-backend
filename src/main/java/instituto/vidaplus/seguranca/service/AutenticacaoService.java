package instituto.vidaplus.seguranca.service;

import instituto.vidaplus.seguranca.dto.RequisicaoRegistro;
import instituto.vidaplus.seguranca.model.Role;
import instituto.vidaplus.seguranca.model.Usuario;
import instituto.vidaplus.seguranca.repository.RoleRepository;
import instituto.vidaplus.seguranca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(RequisicaoRegistro requisicao) {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(requisicao.getUsername());
        usuario.setEmail(requisicao.getEmail());
        usuario.setSenha(passwordEncoder.encode(requisicao.getSenha()));

        if (usuario.getRoles() == null) {
            usuario.setRoles(new HashSet<>());
        }

        // Always add USER role
        Role roleUser = roleRepository.findByNome("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role USER não encontrada"));
        usuario.getRoles().add(roleUser);

        // Add ADMIN role if admin flag is true
        if (requisicao.isAdmin()) {
            Role roleAdmin = roleRepository.findByNome("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN não encontrada"));
            usuario.getRoles().add(roleAdmin);
        }

        return usuarioRepository.save(usuario);
    }
}

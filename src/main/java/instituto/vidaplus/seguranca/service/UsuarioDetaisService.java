package instituto.vidaplus.seguranca.service;

import instituto.vidaplus.seguranca.repository.UsuarioRepository;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetaisService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetaisService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByNomeUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public boolean userExists(String username) {
        return usuarioRepository.existsByNomeUsuario(username);
    }

    public boolean emailExists(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}

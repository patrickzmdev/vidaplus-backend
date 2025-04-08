package instituto.vidaplus.seguranca.service.impl;

import instituto.vidaplus.exception.genericas.EmailJaCadastradoException;
import instituto.vidaplus.exception.genericas.SenhaInvalidaException;
import instituto.vidaplus.exception.genericas.UsuarioJaCadastradoException;
import instituto.vidaplus.seguranca.dto.RequisicaoLogin;
import instituto.vidaplus.seguranca.dto.RequisicaoRegistro;
import instituto.vidaplus.seguranca.exception.UsuarioNaoEncontradoException;
import instituto.vidaplus.seguranca.model.Role;
import instituto.vidaplus.seguranca.model.Usuario;
import instituto.vidaplus.seguranca.repository.RoleRepository;
import instituto.vidaplus.seguranca.repository.UsuarioRepository;
import instituto.vidaplus.seguranca.service.AutenticacaoService;
import instituto.vidaplus.seguranca.token.JwtTokenUtil;
import instituto.vidaplus.utils.validador.ValidadorSenha;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ValidadorSenha validadorSenha;

    public Usuario registrarUsuario(RequisicaoRegistro requisicao) {
        if (usuarioRepository.existsByNomeUsuario(requisicao.getUserName())) {
            throw new UsuarioJaCadastradoException("Usuário já existe");
        }
        if (usuarioRepository.existsByEmail(requisicao.getEmail())) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

        validadorSenha.validarSenha(requisicao.getSenha());
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(requisicao.getUserName());
        usuario.setEmail(requisicao.getEmail());
        usuario.setSenha(passwordEncoder.encode(requisicao.getSenha()));

        if (usuario.getRoles() == null) {
            usuario.setRoles(new HashSet<>());
        }

        Role roleUser = roleRepository.findByNome("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role USER não encontrada"));
        usuario.getRoles().add(roleUser);

        if (requisicao.isAdmin()) {
            Role roleAdmin = roleRepository.findByNome("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN não encontrada"));
            usuario.getRoles().add(roleAdmin);
        }

        return usuarioRepository.save(usuario);
    }

    public String loginUsuario(RequisicaoLogin requisicaoLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requisicaoLogin.getUserName(), requisicaoLogin.getSenha())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Usuario usuario = usuarioRepository.findByNomeUsuario(requisicaoLogin.getUserName())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

            usuario.setTentativasFalhas(0);
            usuario.setUltimoAcesso(LocalDateTime.now());
            usuarioRepository.save(usuario);

            return jwtTokenUtil.generateToken(userDetails);
        }catch (BadCredentialsException e) {
            Usuario usuario = usuarioRepository.findByNomeUsuario(requisicaoLogin.getUserName())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

            usuario.setTentativasFalhas(usuario.getTentativasFalhas() + 1);
            usuarioRepository.save(usuario);

            if (usuario.getTentativasFalhas() >= 3) {
                usuario.setEnabled(false);
                usuarioRepository.save(usuario);
                throw new UsuarioNaoEncontradoException("Usuário bloqueado");
            }

            throw new SenhaInvalidaException("Senha incorreta: você tem " + (3 - usuario.getTentativasFalhas()) + " tentativas restantes");
        }
    }
}

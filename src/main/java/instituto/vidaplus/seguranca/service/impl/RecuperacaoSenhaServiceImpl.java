package instituto.vidaplus.seguranca.service.impl;

import instituto.vidaplus.email.service.EmailService;
import instituto.vidaplus.seguranca.exception.UsuarioNaoEncontradoException;
import instituto.vidaplus.seguranca.model.Usuario;
import instituto.vidaplus.seguranca.repository.UsuarioRepository;
import instituto.vidaplus.seguranca.service.RecuperacaoSenhaService;
import instituto.vidaplus.utils.validador.ValidadorSenha;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecuperacaoSenhaServiceImpl implements RecuperacaoSenhaService {

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final ValidadorSenha validadorSenha;

    private final Map<String, Object[]> codigosRecuperacao = new HashMap<>();

    @Override
    public void requisitarRecuperacaoSenha(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if(usuario.isPresent()) {
            Usuario usuarioRecuperado = usuario.get();
            String codigo = gerarCodigoRecuperacao();
            codigosRecuperacao.put(email, new Object[]{codigo, LocalDateTime.now().plusMinutes(30)});
            emailService.enviarCodigoDeRecuperacao(usuarioRecuperado, codigo);
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }
    }

    @Override
    public Boolean validarSenhaDeRecuperacao(String email, String senha) {
        if(!codigosRecuperacao.containsKey(email)) {
            return false;
        }

        Object[] dados = codigosRecuperacao.get(email);
        String codigo = (String) dados[0];
        LocalDateTime expiracao = (LocalDateTime) dados[1];

        return codigo.equals(senha) && LocalDateTime.now().isBefore(expiracao);
    }

    @Override
    public void resetarSenha(String email, String codigo, String novaSenha) {
        if (!validarSenhaDeRecuperacao(email, codigo)) {
            throw new RuntimeException("Código inválido ou expirado");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        validadorSenha.validarSenha(novaSenha);
        Usuario usuario = usuarioOpt.get();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
        codigosRecuperacao.remove(email);
    }

    private String gerarCodigoRecuperacao() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(100000+ random.nextInt(900000));
    }
}

package instituto.vidaplus.seguranca.controller;

import instituto.vidaplus.seguranca.dto.JwtResponse;
import instituto.vidaplus.seguranca.dto.RequisicaoLogin;
import instituto.vidaplus.seguranca.dto.RequisicaoRegistro;
import instituto.vidaplus.seguranca.model.Usuario;
import instituto.vidaplus.seguranca.service.impl.AutenticacaoServiceImpl;
import instituto.vidaplus.seguranca.service.UsuarioDetaisServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final UsuarioDetaisServiceImpl usuarioDetaisServiceImpl;
    private final AutenticacaoServiceImpl autenticacaoService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RequisicaoRegistro requisicaoRegistro) {
        if (usuarioDetaisServiceImpl.userExists(requisicaoRegistro.getUsername())) {
            return ResponseEntity.badRequest().body("Nome de usuário já existe");
        }
        if (usuarioDetaisServiceImpl.emailExists(requisicaoRegistro.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        Usuario usuario = autenticacaoService.registrarUsuario(requisicaoRegistro);

        return ResponseEntity.ok("Usuário registrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody RequisicaoLogin requisicaoLogin) {
        try {
            String jwt = autenticacaoService.loginUsuario(requisicaoLogin);
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
}

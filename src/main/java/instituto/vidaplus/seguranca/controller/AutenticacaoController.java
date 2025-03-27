package instituto.vidaplus.seguranca.controller;

import instituto.vidaplus.seguranca.dto.JwtResponse;
import instituto.vidaplus.seguranca.dto.RequisicaoLogin;
import instituto.vidaplus.seguranca.dto.RequisicaoRegistro;
import instituto.vidaplus.seguranca.enums.UserRoles;
import instituto.vidaplus.seguranca.model.Role;
import instituto.vidaplus.seguranca.model.Usuario;
import instituto.vidaplus.seguranca.repository.RoleRepository;
import instituto.vidaplus.seguranca.repository.UsuarioRepository;
import instituto.vidaplus.seguranca.service.AutenticacaoService;
import instituto.vidaplus.seguranca.service.UsuarioDetaisService;
import instituto.vidaplus.seguranca.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final UsuarioDetaisService usuarioDetaisService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RoleRepository roleRepository;
    private final AutenticacaoService autenticacaoService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RequisicaoRegistro requisicaoRegistro) {
        if (usuarioDetaisService.userExists(requisicaoRegistro.getUsername())) {
            return ResponseEntity.badRequest().body("Nome de usuário já existe");
        }
        if (usuarioDetaisService.emailExists(requisicaoRegistro.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        Usuario usuario = autenticacaoService.registrarUsuario(requisicaoRegistro);

        return ResponseEntity.ok("Usuário registrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody RequisicaoLogin requisicaoLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requisicaoLogin.getUsername(), requisicaoLogin.getSenha())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
}

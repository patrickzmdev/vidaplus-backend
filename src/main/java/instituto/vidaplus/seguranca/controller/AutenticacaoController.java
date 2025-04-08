package instituto.vidaplus.seguranca.controller;

import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import instituto.vidaplus.seguranca.dto.JwtResponse;
import instituto.vidaplus.seguranca.dto.RequisicaoLogin;
import instituto.vidaplus.seguranca.dto.RequisicaoRegistro;
import instituto.vidaplus.seguranca.model.Usuario;
import instituto.vidaplus.seguranca.service.impl.AutenticacaoServiceImpl;
import instituto.vidaplus.seguranca.service.UsuarioDetaisServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Registrar Usuário",
            description = "Cria um novo usuário no sistema")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RequisicaoRegistro.class),
                    examples = @ExampleObject(
                            value = "{\"userName\": \"user\", \"email\": \"teste@usuario.com\", \"senha\": \"Mepa231?\", \"admin\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "201",
            description = "Usuário criado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RequisicaoRegistro.class),
                    examples = @ExampleObject(
                            value = "{\"userName\": \"user\", \"email\": \"teste@usuario.com\", \"senha\": \"Mepa231?\", \"admin\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Senha inválida",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"A senha deve ter no mínimo 8 caracteres. A senha deve conter pelo menos um caractere especial\"}"
                    )
            )
    )
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RequisicaoRegistro requisicaoRegistro) {
        if (usuarioDetaisServiceImpl.userExists(requisicaoRegistro.getUserName())) {
            return ResponseEntity.badRequest().body("Nome de usuário já existe");
        }
        if (usuarioDetaisServiceImpl.emailExists(requisicaoRegistro.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        Usuario usuario = autenticacaoService.registrarUsuario(requisicaoRegistro);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso");
    }

    @Operation(summary = "Login Usuário",
            description = "Realiza o login do usuário no sistema")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RequisicaoLogin.class),
                    examples = @ExampleObject(
                            value = "{\"userName\": \"user\", \"senha\": \"teste123?\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "200",
            description = "Login realizado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RequisicaoLogin.class),
                    examples = @ExampleObject(
                            value = "{\"userName\": \"user\", \"senha\": \"teste123?\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Senha inválida",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Senha incorreta: você tem 2 tentativas restantes\"}"
                    )
            )
    )
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

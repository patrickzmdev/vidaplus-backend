package instituto.vidaplus.seguranca.controller;

import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import instituto.vidaplus.seguranca.dto.RecuperacaoSenhaRequest;
import instituto.vidaplus.seguranca.dto.ResetSenhaRequest;
import instituto.vidaplus.seguranca.dto.ValidacaoCodigoRequest;
import instituto.vidaplus.seguranca.exception.UsuarioNaoEncontradoException;
import instituto.vidaplus.seguranca.service.RecuperacaoSenhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recuperacao-senha")
@RequiredArgsConstructor
public class RecuperacaoSenhaController {

    private final RecuperacaoSenhaService recuperacaoSenhaService;

    @Operation(summary = "Enviar código de recuperação de senha",
            description = "Envia um código de recuperação de senha para o email do usuário")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Email do usuário para recuperação de senha",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecuperacaoSenhaRequest.class),
                    examples = @ExampleObject(
                            value = "{\"email\": \"teste@teste.com\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "200",
            description = "Código de recuperação enviado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecuperacaoSenhaRequest.class),
                    examples = @ExampleObject(
                            value = "{\"email\": \"teste@teste.com\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Email não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Email não encontrado\"}"
                    )
            )
    )
    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitarRecuperacao(@RequestBody RecuperacaoSenhaRequest request) {
        try {
            recuperacaoSenhaService.requisitarRecuperacaoSenha(request.getEmail());
            return ResponseEntity.ok("Código de recuperação enviado para o email");
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Email não encontrado");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar solicitação");
        }
    }

    @Operation(summary = "Validar código de recuperação de senha",
            description = "Efetua a validação do código de recuperação de senha enviado para o email do usuário")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Email e código de recuperação de senha",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecuperacaoSenhaRequest.class),
                    examples = @ExampleObject(
                            value = "{\"email\": \"teste@teste.com\", \"codigo\": \"123456\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "200",
            description = "Código de recuperação enviado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecuperacaoSenhaRequest.class),
                    examples = @ExampleObject(
                            value = "{\"email\": \"teste@teste.com\", \"codigo\": \"123456\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Código inválido ou expirado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Código inválido ou expirado\"}"
                    )
            )
    )
    @PostMapping("/validar-codigo")
    public ResponseEntity<?> validarCodigo(@RequestBody ValidacaoCodigoRequest request) {
        Boolean isValido = recuperacaoSenhaService.validarSenhaDeRecuperacao(
                request.getEmail(), request.getCodigo());
        if (isValido) {
            return ResponseEntity.ok("Código válido");
        } else {
            return ResponseEntity.badRequest().body("Código inválido ou expirado");
        }
    }

    @Operation(summary = "Redefinir senha",
            description = "Efetua a redefinição da senha do usuário")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request para redefinição de senha",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecuperacaoSenhaRequest.class),
                    examples = @ExampleObject(
                            value = "{\"email\": \"teste@teste.com\", \"codigo\": \"123456\", \"novaSenha\": \"NovaSenha123!\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "200",
            description = "Senha redefinida com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecuperacaoSenhaRequest.class),
                    examples = @ExampleObject(
                            value = "{\"email\": \"teste@teste.com\", \"codigo\": \"123456\", \"novaSenha\": \"NovaSenha123!\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Erro ao redefinir senha",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Não foi possível redefinir a senha\"}"
                    )
            )
    )
    @PostMapping("/redefinir")
    public ResponseEntity<?> redefinirSenha(@RequestBody ResetSenhaRequest request) {
        try {
            recuperacaoSenhaService.resetarSenha(
                    request.getEmail(), request.getCodigo(), request.getNovaSenha());
            return ResponseEntity.ok("Senha redefinida com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não foi possível redefinir a senha");
        }
    }
}

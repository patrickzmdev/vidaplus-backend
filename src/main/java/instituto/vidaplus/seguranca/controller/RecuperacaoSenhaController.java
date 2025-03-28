package instituto.vidaplus.seguranca.controller;

import instituto.vidaplus.seguranca.dto.RecuperacaoSenhaRequest;
import instituto.vidaplus.seguranca.dto.ResetSenhaRequest;
import instituto.vidaplus.seguranca.dto.ValidacaoCodigoRequest;
import instituto.vidaplus.seguranca.exception.UsuarioNaoEncontradoException;
import instituto.vidaplus.seguranca.service.RecuperacaoSenhaService;
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

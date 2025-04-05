package instituto.vidaplus.internacao.controller;

import instituto.vidaplus.exame.dto.ExameSuprimentoDTO;
import instituto.vidaplus.internacao.dto.InternacaoDTO;
import instituto.vidaplus.internacao.dto.InternacaoSuprimentoDto;
import instituto.vidaplus.internacao.service.InternacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internacoes")
public class InternacaoController {

    private final InternacaoService internacaoService;

    @Operation(summary = "Registrar nova internação",
            description = "Esta operação registra uma nova internação para um paciente em um leito específico.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da internação",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InternacaoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"motivoInternacao\": \"virose\", \"medicoResponsavelId\": 1}"
                    )
            )
    )
    @ApiResponse(responseCode = "201",
            description = "Internação criada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InternacaoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"motivoInternacao\": \"virose\", \"medicoResponsavelId\": 1}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Leito não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Leito não encontrado\"}"
                    )
            )
    )
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<InternacaoDTO> registrarInternacao(@Parameter(description = "Id do paciente", example = "1", required = true) @RequestParam Long pacienteId,
                                                             @Parameter(description = "Id do leito", example = "1", required = true)@RequestParam Long leitoId,
                                                             @RequestBody InternacaoDTO internacao) {
        InternacaoDTO internacaoRegistrada = internacaoService.registrarInternacao(pacienteId, leitoId, internacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(internacaoRegistrada);
    }

    @Operation(summary = "Encerrar Internação",
            description = "Esta operação encerra uma internação existente.")
    @ApiResponse(responseCode = "200",
            description = "Internação encerrada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Internação encerrada com sucesso\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Internação não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Internação não encontrada\"}"
                    )
            )
    )
    @PutMapping("/encerrar/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String encerrarInternacao(@Parameter(description = "Id da internação", example = "1", required = true)@PathVariable Long id){
        return internacaoService.encerrarInternacao(id);
    }

    @Operation(summary = "Transferir Paciente",
            description = "Essa operação transfere um paciente para outro leito.")
    @ApiResponse(responseCode = "200",
            description = "Paciente transferido com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"pacienteId\": 1, \"leitoId\": 2, \"dataAdmissao\": \"2025-04-04T20:34:53\", \"motivoInternacao\": \"virose\", \"medicoResponsavelId\": 1, \"ativa\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Leito não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Leito não encontrado\"}"
                    )
            )
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<InternacaoDTO> transferirPaciente(
            @Parameter(description = "Id da internação", example = "1", required = true)@PathVariable Long id,
            @Parameter(description = "Id do leito", example = "1", required = true)@RequestParam Long leitoId) {
        InternacaoDTO internacaoTransferida = internacaoService.transferirPaciente(id, leitoId);
        return ResponseEntity.ok(internacaoTransferida);
    }

    @Operation(summary = "Adicionar suprimento a internação",
            description = "Associa um suprimento a uma internação existente.")
    @ApiResponse(responseCode = "200",
            description = "Suprimento adicionado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExameSuprimentoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"internacaoId\": 1, \"suprimentoId\": 1, \"quantidadeUtilizada\": 2}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Internação não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Internação não encontrada\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "400",
            description = "Quantidade insuficiente no estoque",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Quantidade de suprimento insuficiente\"}"
                    )
            )
    )
    @PostMapping("/suprimento/{internacaoId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<InternacaoSuprimentoDto> adicionarSuprimentoAUmaInternacao(
            @Parameter(description = "Id da internação", example = "1", required = true)@PathVariable Long internacaoId,
            @Parameter(description = "Id do suprimento", example = "1", required = true)@RequestParam Long suprimentoId,
            @Parameter(description = "Quantidade utilizada", example = "10", required = true)@RequestParam Integer quantidadeUtilizada) {
        InternacaoSuprimentoDto internacaoSuprimento = internacaoService.adicionarSuprimentoAUmaInternacao(internacaoId, suprimentoId, quantidadeUtilizada);
        return ResponseEntity.ok(internacaoSuprimento);
    }
}

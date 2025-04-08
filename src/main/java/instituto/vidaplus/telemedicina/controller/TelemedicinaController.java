package instituto.vidaplus.telemedicina.controller;

import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import instituto.vidaplus.telemedicina.dto.TelemedicinaDTO;
import instituto.vidaplus.telemedicina.service.TelemedicinaService;
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
@RequestMapping("/telemedicina")
@RequiredArgsConstructor
public class TelemedicinaController {

    private final TelemedicinaService telemedicinaService;

    @Operation(summary = "Criar Telemedicina",
            description = "Cria uma nova telemedicina associada a uma consulta")
    @ApiResponse(responseCode = "201",
            description = "Telemedicina criada sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TelemedicinaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"consultaId\": 1, \"linkVideoChamada\": \"https://vidaplus.com.br/telemedicina/fc5279c8-a1bf-457d-a653-21773f1e7ba2\", \"videoChamadaSegura\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Consulta não encontrada"
    )
    @PostMapping("/{consultaId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> criarTelemedicina(
            @Parameter(description = "Id da consulta", example = "1", required = true) @PathVariable Long consultaId) {
        TelemedicinaDTO telemedicinaAdicionada = telemedicinaService.criarTelemedicina(consultaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(telemedicinaAdicionada);
    }

    @Operation(summary = "Buscar telemedicina por ID",
            description = "Efetua a busca de uma telemedicina pelo ID da consulta")
    @ApiResponse(responseCode = "200",
            description = "Telemedicina encontrada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TelemedicinaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"consultaId\": 1, \"linkVideoChamada\": \"https://vidaplus.com.br/telemedicina/fc5279c8-a1bf-457d-a653-21773f1e7ba2\", \"videoChamadaSegura\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Consulta não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Consulta não encontrada\"}"
                    )
            )
    )
    @GetMapping("/{consultaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> buscarTelemedicinaPorConsultaId(
            @Parameter(description = "Id da consulta", example = "1", required = true)@PathVariable Long consultaId) {
        TelemedicinaDTO telemedicinaBuscada = telemedicinaService.buscarTelemedicinaPorConsultaId(consultaId);
        return ResponseEntity.ok(telemedicinaBuscada);
    }

    @Operation(summary = "Atualizar link telemedicina",
            description = "Gera um novo link de telemedicina para uma consulta existente")
    @ApiResponse(responseCode = "201",
            description = "Link atualizado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TelemedicinaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"consultaId\": 1, \"linkVideoChamada\": \"https://vidaplus.com.br/telemedicina/fc5279c8-a1bf-457d-a653-21773f1e7ba2\", \"videoChamadaSegura\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Telemedicina não encontrada"
    )
    @PutMapping("/{telemedicinaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> atualizarLinkTelemedicina(
            @Parameter(description = "Id da telemedicina", example = "1", required = true)@PathVariable Long telemedicinaId) {
        TelemedicinaDTO telemedicinaAtualizada = telemedicinaService.atualizarLinkTelemedicina(telemedicinaId);
        return ResponseEntity.ok(telemedicinaAtualizada);
    }

    @Operation(summary = "Deletar Telemedicina",
            description = "Exclui uma telemedicina pelo ID")
    @ApiResponse(responseCode = "200", description = "Telemedicina removida com sucesso",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Telemedicina removida com sucesso\"}")))
    @ApiResponse(responseCode = "404", description = "Telemedicina não encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Telemedicina não encontrada\"}")))
    @DeleteMapping("/{telemedicinaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String deletarTelemedicina(
            @Parameter(description = "Id da telemedicina", example = "1", required = true)@PathVariable Long telemedicinaId) {
        return telemedicinaService.deletarTelemedicina(telemedicinaId);
    }

    @Operation(summary = "Verificar se consulta está em telemedicina",
            description = "Verifica se uma consulta está associada a uma telemedicina")
    @ApiResponse(responseCode = "200", description = "Consulta verificada com sucesso",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "true")))
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Consulta não encontrada\"}")))
    @GetMapping("/consulta/{consultaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Boolean consultaEstaEmTelemedicina(
            @Parameter(description = "Id da consulta", example = "1", required = true)@PathVariable Long consultaId) {
        return telemedicinaService.consultaEstaEmTelemedicina(consultaId);
    }

    @Operation(summary = "Iniciar Telemedicina",
            description = "Inicia uma telemedicina existente pelo ID")
    @ApiResponse(responseCode = "200", description = "Telemedicina iniciada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TelemedicinaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"consultaId\": 1, \"linkVideoChamada\": \"https://vidaplus.com.br/telemedicina/fc5279c8-a1bf-457d-a653-21773f1e7ba2\", \"videoChamadaSegura\": true}"
                    )))
    @ApiResponse(responseCode = "404", description = "Telemedicina não encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Telemedicina não encontrada\"}")))
    @PutMapping("/iniciar/{telemedicinaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> iniciarTelemedicina(
            @Parameter(description = "Id da telemedicina", example = "1", required = true)@PathVariable Long telemedicinaId) {
        TelemedicinaDTO telemedicinaIniciada = telemedicinaService.iniciarTelemedicina(telemedicinaId);
        return ResponseEntity.ok(telemedicinaIniciada);
    }

    @Operation(summary = "Finalizar Telemedicina",
            description = "Finaliza uma telemedicina existente pelo ID")
    @ApiResponse(responseCode = "200", description = "Telemedicina finalizada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TelemedicinaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"consultaId\": 1, \"linkVideoChamada\": \"https://vidaplus.com.br/telemedicina/fc5279c8-a1bf-457d-a653-21773f1e7ba2\", \"videoChamadaSegura\": true}"
                    )))
    @ApiResponse(responseCode = "404", description = "Telemedicina não encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Telemedicina não encontrada\"}")))
    @PutMapping("/finalizar/{telemedicinaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> finalizarTelemedicina(@PathVariable Long telemedicinaId) {
        TelemedicinaDTO telemedicinaFinalizada = telemedicinaService.finalizarTelemedicina(telemedicinaId);
        return ResponseEntity.ok(telemedicinaFinalizada);
    }
}

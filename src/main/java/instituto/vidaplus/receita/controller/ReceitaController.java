package instituto.vidaplus.receita.controller;

import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import instituto.vidaplus.receita.dto.ReceitaDTO;
import instituto.vidaplus.receita.service.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;

    @Operation(summary = "Criar receita",
            description = "Cria um nova receita associada a uma consulta")
    @ApiResponse(responseCode = "201",
            description = "Receita criada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReceitaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 0, \"pacienteId\": 1, \"profissionalId\": 3, \"consultaId\": 6, \"dataEmissao\": \"2025-04-07\", \"dataFimValidade\": \"2025-05-07\", \"medicamentos\": []}"
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
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReceitaDTO> criarReceita(@RequestParam Long consultaId) {
        ReceitaDTO receitaAdicionada = receitaService.criarReceita(consultaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(receitaAdicionada);
    }

    @Operation(summary = "Buscar receita por ID",
            description = "Busca uma receita pelo seu ID")
    @ApiResponse(responseCode = "200",
            description = "Receita encontrada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReceitaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 0, \"pacienteId\": 1, \"profissionalId\": 3, \"consultaId\": 6, \"dataEmissao\": \"2025-04-07\" ,\"dataFimValidade\": \"2025-05-07\", \"medicamentos\": []}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Receita não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Receita não encontrada\"}"
                    )
            )
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReceitaDTO> buscarReceitaPorId(
            @Parameter(description = "Id da receita", example = "1", required = true) @PathVariable Long id) {
        ReceitaDTO receitaBuscada = receitaService.buscarReceitaPorId(id);
        return ResponseEntity.ok(receitaBuscada);
    }

    @Operation(summary = "Buscar receita por paciente",
            description = "Busca uma receita pelo ID do paciente")
    @ApiResponse(responseCode = "200",
            description = "Receita encontrada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReceitaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 0, \"pacienteId\": 1, \"profissionalId\": 3, \"consultaId\": 6, \"dataEmissao\": \"2025-04-07\" ,\"dataFimValidade\": \"2025-05-07\", \"medicamentos\": []}"
                    )
            )
    )
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Tamanho da página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Ordenação: propriedade,direção (ex: id,asc)", schema = @Schema(type = "string", defaultValue = "id,asc"))
    @GetMapping("/paciente/{pacienteId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ReceitaDTO>> buscarReceitasPorPacienteId(
            @Parameter(description = "Id do paciente", example = "1", required = true)@PathVariable Long pacienteId, Pageable pageable) {
        Page<ReceitaDTO> receitas = receitaService.buscarReceitasPorPacienteId(pacienteId, pageable);
        return ResponseEntity.ok(receitas);
    }

    @Operation(summary = "Buscar receita por profissional",
            description = "Busca uma receita pelo ID do profissional")
    @ApiResponse(responseCode = "200",
            description = "Receita encontrada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReceitaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 0, \"pacienteId\": 1, \"profissionalId\": 3, \"consultaId\": 6, \"dataEmissao\": \"2025-04-07\" ,\"dataFimValidade\": \"2025-05-07\", \"medicamentos\": []}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Profissional não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Profissional não encontrado\"}"
                    )
            )
    )
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Tamanho da página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Ordenação: propriedade,direção (ex: id,asc)", schema = @Schema(type = "string", defaultValue = "id,asc"))
    @GetMapping("/profissional/{profissionalId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ReceitaDTO>> buscarReceitasPorProfissionalId(
            @Parameter(description = "Id do profissional", example = "1", required = true)@PathVariable Long profissionalId, Pageable pageable) {
        Page<ReceitaDTO> receitas = receitaService.buscarReceitasPorProfissionalId(profissionalId, pageable);
        return ResponseEntity.ok(receitas);
    }

    @Operation(summary = "Buscar receita por consulta",
            description = "Busca uma receita pelo ID da consulta")
    @ApiResponse(responseCode = "200",
            description = "Receita encontrada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReceitaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 0, \"pacienteId\": 1, \"profissionalId\": 3, \"consultaId\": 6, \"dataEmissao\": \"2025-04-07\" ,\"dataFimValidade\": \"2025-05-07\", \"medicamentos\": []}"
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
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Tamanho da página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Ordenação: propriedade,direção (ex: id,asc)", schema = @Schema(type = "string",  defaultValue = "id,asc"))
    @GetMapping("/consulta/{consultaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ReceitaDTO>> buscarReceitaPorConsultaId(
            @Parameter(description = "Id da consulta", example = "1", required = true)@PathVariable Long consultaId, Pageable pageable) {
        Page<ReceitaDTO> receitas = receitaService.buscarReceitaPorConsultaId(consultaId, pageable);
        return ResponseEntity.ok(receitas);
    }

    @Operation(summary = "Buscar receitas por período",
            description = "Busca receitas de um paciente em um período específico")
    @ApiResponse(responseCode = "200", description = "Receitas encontradas com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReceitaDTO.class),
                    examples = @ExampleObject(value = "[{\"id\": 0, \"pacienteId\": 1, \"profissionalId\": 3, \"consultaId\": 6, \"dataEmissao\": \"2025-04-07\", \"dataFimValidade\": \"2025-05-07\", \"medicamentos\": []}]")))
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Paciente não encontrado\"}")))
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Tamanho da página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Ordenação: propriedade,direção (ex: id,asc)", schema = @Schema(type = "string",  defaultValue = "id,asc"))
    @GetMapping("/periodo")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ReceitaDTO>> buscarReceitasPorPeriodo(
            @Parameter(description = "Id do paciente", example = "1", required = true) @RequestParam Long pacienteId,
            @Parameter(description = "Data de início do período", example = "2025-01-01", required = true) @RequestParam LocalDate dataInicio,
            @Parameter(description = "Data de fim do período", example = "2025-12-31", required = true) @RequestParam LocalDate dataFim,
             Pageable pageable) {
        Page<ReceitaDTO> receitas = receitaService.buscarReceitasPorPeriodo(pacienteId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(receitas);
    }

    @Operation(summary = "Gerar receita",
            description = "Gera em PDF de uma receita específica")
    @ApiResponse(responseCode = "200", description = "Receita gerada com sucesso",
            content = @Content(mediaType = "application/pdf",
                    examples = @ExampleObject(value = "PDF content")))
    @ApiResponse(responseCode = "404", description = "Receita não encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Receita não encontrada\"}")))
    @GetMapping("/{id}/relatorio")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<byte[]> gerarReceita(@PathVariable Long id) {
        byte[] relatorio = receitaService.gerarReceita(id);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=relatorio-receita.pdf")
                .body(relatorio);
    }
}

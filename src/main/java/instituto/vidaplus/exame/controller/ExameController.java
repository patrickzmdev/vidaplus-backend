package instituto.vidaplus.exame.controller;

import instituto.vidaplus.agenda.dto.AgendaDTO;
import instituto.vidaplus.consulta.dto.ConsultaDTO;
import instituto.vidaplus.exame.dto.ExameDTO;
import instituto.vidaplus.exame.dto.ExameSuprimentoDTO;
import instituto.vidaplus.exame.service.ExameService;
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

@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExameController {

    private final ExameService exameService;

    @Operation(summary = "Agendar exame",
            description = "Realizar o agendamento de um exame para um paciente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto dados do exame",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExameDTO.class),
                    examples = @ExampleObject(
                            value = "{\"profissionalId\": 1, \"tipoExame\": \"HEMOGRAMA\", \"dataAgendamento\": \"2025-04-13\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "201",
            description = "Exame agendado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExameDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"pacienteId\": 1, \"profissionalId\": 1, \"tipoExame\": \"HEMOGRAMA\", \"dataAgendamento\": \"2025-04-13\", \"status\": \"AGENDADO\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Profissional não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"profissionalId\": 15, \"tipoExame\": \"HEMOGRAMA\", \"dataAgendamento\": \"2025-04-13\"}"
                    )
            )
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ExameDTO> agendarExame(
            @Parameter(description = "Id do paciente", example = "1", required = true) @RequestParam Long pacienteId,
            @RequestBody ExameDTO exameDTO) {
        ExameDTO exameAgendado = exameService.agendarExame(pacienteId, exameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(exameAgendado);
    }

    @Operation(summary = "Buscar exame",
            description = "Faz a busca de um exame pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Exame encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExameDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1,\"pacienteId\": 1,\"profissionalId\": 1,\"tipoExame\": \"HEMOGRAMA\", \"dataAgendamento\": \"2025-04-13\", \"status\": \"AGENDADO\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Exame não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Exame não encontrado\"}"
                    )
            )
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ExameDTO> buscarExame(
            @Parameter(description = "Id do exame", example = "1", required = true)@PathVariable Long id) {
        ExameDTO exame = exameService.buscarExame(id);
        return ResponseEntity.ok(exame);
    }

    @Operation(summary = "Buscar exames por paciente",
            description = "Faz a busca de exames pelo ID do paciente")
    @ApiResponse(responseCode = "200",
            description = "Resultado da busca",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExameDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1,\"pacienteId\": 1,\"profissionalId\": 1,\"tipoExame\": \"HEMOGRAMA\", \"dataAgendamento\": \"2025-04-13\", \"status\": \"AGENDADO\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Paciente não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Paciente não encontrado\"}"
                    )
            )
    )
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Tamanho da página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Ordenação: propriedade,direção (ex: id,asc)", schema = @Schema(type = "string", defaultValue = "id,asc"))
    @GetMapping("/paciente/{pacienteId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ExameDTO>> buscarExamesPorPaciente(
            @Parameter(description = "Id do paciente", example = "1", required = true)@PathVariable Long pacienteId, Pageable pageable) {
        Page<ExameDTO> exames = exameService.buscarExamesPorPaciente(pacienteId, pageable);
        return ResponseEntity.ok(exames);
    }

    @Operation(summary = "Finalizar exame",
            description = "Informa que o exame foi realizado")
    @ApiResponse(responseCode = "200",
            description = "Exame finalizado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Exame finalizado com sucesso\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Exame não encontrad0",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Exame não encontrado\"}"
                    )
            )
    )
    @PutMapping("/finalizar/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String finalizarExame(
            @Parameter(description = "Id do exame", example = "1", required = true)@PathVariable Long id) {
        return exameService.finalizarExame(id);
    }

    @Operation(summary = "Cancelar exame",
            description = "Informa que o exame foi cancelado")
    @ApiResponse(responseCode = "200",
            description = "Exame cancelado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Exame cancelado com sucesso\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Exame não encontrad0",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Exame não encontrado\"}"
                    )
            )
    )
    @PutMapping("/cancelar/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String cancelarExame(@Parameter(description = "Id do exame", example = "1", required = true)
                                    @PathVariable Long id) {
        return exameService.cancelarExame(id);
    }

    @Operation(summary = "Adicionar suprimento ao exame",
            description = "Associa um suprimento a um exame com quantidade utilizada")
    @ApiResponse(responseCode = "200",
            description = "Suprimento adicionado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExameSuprimentoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"exameId\": 1, \"suprimentoId\": 1, \"quantidadeUtilizada\": 2}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Exame não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Exame não encontrado\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "400",
            description = "Quantidade insuficiente no estoque",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Estoque insuficiente para o suprimento: Seringa\"}"
                    )
            )
    )
    @PostMapping("/suprimento/{exameId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ExameSuprimentoDTO> adicionarSuprimentoAExame(
            @Parameter(description = "Id do exame", example = "1", required = true)@PathVariable Long exameId,
            @Parameter(description = "Id do suprimento", example = "1", required = true)@RequestParam Long suprimentoId,
            @Parameter(description = "Quantidade utilizada", example = "10", required = true)@RequestParam Integer quantidadeUtilizada) {
        ExameSuprimentoDTO exameSuprimento = exameService.adicionarSuprimentoAExame(exameId, suprimentoId, quantidadeUtilizada);
        return ResponseEntity.ok(exameSuprimento);
    }
}

package instituto.vidaplus.consulta.controller;

import instituto.vidaplus.agenda.dto.AgendaDTO;
import instituto.vidaplus.consulta.dto.ConsultaDTO;
import instituto.vidaplus.consulta.service.ConsultaService;
import instituto.vidaplus.documentacao.exemplos.agenda.PageAgendaDTO;
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
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @Operation(summary = "Criar consulta",
            description = "Cria uma nova consulta para um paciente em uma agenda específica")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto com dados da consulta",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ConsultaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"data\": \"2025-04-07\", \"horaInicio\": \"11:01:00\", \"horaFim\": \"11:10:00\", \"motivoConsulta\": \"gripe\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "A data da consulta não pode ser anterior a data atual",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"data\": \"2023-04-07\", \"horaInicio\": \"11:01:00\", \"horaFim\": \"11:10:00\", \"motivoConsulta\": \"gripe\"}"
                    )
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ConsultaDTO> criarConsulta(
            @Parameter(description = "Id do paciente", example = "1", required = true)@RequestParam Long pacienteId,
            @Parameter(description = "Id da agenda", example = "1", required = true) @RequestParam Long agendaId,
            @RequestBody ConsultaDTO consultaDTO) {
        ConsultaDTO consultaAdicionada = consultaService.criarConsulta(pacienteId, agendaId, consultaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaAdicionada);
    }

    @Operation(summary = "Buscar consulta",
            description = "Faz a busca de uma consulta pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Consulta encontrada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1,\"pacienteId\": 1,\"profissionalId\": 1,\"agendaId\": 1, \"telemedicinaId\": null, \"linkVideoChamada\": null, \"data\": \"2025-04-07\", \"horaInicio\": \"11:01:00\", \"horaFim\": \"11:10:00\", \"status\": \"AGENDADA\", \"motivoConsulta\": \"gripe\"}"
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
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ConsultaDTO> buscarConsultaPorId(
            @Parameter(description = "Id da consulta", example = "1", required = true)
            @PathVariable Long id) {
        ConsultaDTO consultaBuscada = consultaService.buscarConsultaPorId(id);
        return ResponseEntity.ok(consultaBuscada);
    }

    @Operation(summary = "Buscar todas as consultas",
            description = "Faz a busca de todas as consultas cadastradas com suporte a paginação")
    @ApiResponse(responseCode = "200",
            description = "Consultas encontradas com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PageAgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"content\":[{\"id\": 1,\"pacienteId\": 1,\"profissionalId\": 1,\"agendaId\": 1, \"data\": \"2025-04-07\", \"horaInicio\": \"11:01:00\", \"horaFim\": \"11:10:00\", \"status\": \"AGENDADA\", \"motivoConsulta\": \"gripe\"}],\"pageable\":{\"pageNumber\":0,\"pageSize\":20},\"totalElements\":1,\"totalPages\":1}"
                    )
            )
    )
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Tamanho da página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Ordenação: propriedade,direção (ex: id,asc)", schema = @Schema(type = "string", defaultValue = "id,asc"))
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ConsultaDTO>> listarConsultas(Pageable pageable) {
        Page<ConsultaDTO> consultas = consultaService.listarConsultas(pageable);
        return ResponseEntity.ok(consultas);
    }

    @Operation(summary = "Deletar consulta",
            description = "Deleta uma consulta pelo ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> deletarConsulta(
            @Parameter(description = "ID da consulta", example = "1", required = true)
            @PathVariable Long id) {
        String mensagem = consultaService.deletarConsulta(id);
        return ResponseEntity.ok(mensagem);
    }

    @Operation(summary = "Buscar consulta por paciente",
            description = "Faz a busca de uma consulta pelo ID do paciente")
    @ApiResponse(responseCode = "200",
            description = "Resultado da busca",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"content\":[{\"id\": 1,\"pacienteId\": 1,\"profissionalId\": 1,\"agendaId\": 1, \"data\": \"2025-04-07\", \"horaInicio\": \"11:01:00\", \"horaFim\": \"11:10:00\", \"status\": \"AGENDADA\", \"motivoConsulta\": \"gripe\"}],\"pageable\":{\"pageNumber\":0,\"pageSize\":20},\"totalElements\":1,\"totalPages\":1}"
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
    public ResponseEntity<Page<ConsultaDTO>> buscarConsultasPorPaciente(
            @Parameter(description = "Id do paciente", example = "1", required = true)@PathVariable Long pacienteId,
            @Parameter Pageable pageable) {
        Page<ConsultaDTO> consultasPorPaciente = consultaService.buscarConsultasPorPaciente(pacienteId, pageable);
        return ResponseEntity.ok(consultasPorPaciente);
    }

    @Operation(summary = "Buscar consulta por profissional",
            description = "Faz a busca de uma consulta pelo ID do profissional")
    @ApiResponse(responseCode = "200",
            description = "Resultado da busca",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"content\":[{\"id\": 1,\"pacienteId\": 1,\"profissionalId\": 1,\"agendaId\": 1, \"data\": \"2025-04-07\", \"horaInicio\": \"11:01:00\", \"horaFim\": \"11:10:00\", \"status\": \"AGENDADA\", \"motivoConsulta\": \"gripe\"}],\"pageable\":{\"pageNumber\":0,\"pageSize\":20},\"totalElements\":1,\"totalPages\":1}"
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
    public ResponseEntity<Page<ConsultaDTO>> buscarConsultasPorProfissional(
            @Parameter(description = "Id do profissional", example = "1", required = true)@PathVariable Long profissionalId, Pageable pageable) {
        Page<ConsultaDTO> consultasPorProfissional = consultaService.buscarConsultasPorProfissional(profissionalId, pageable);
        return ResponseEntity.ok(consultasPorProfissional);
    }

    @Operation(summary = "Efetua confirmação da consulta",
            description = "Confirma uma consulta pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Consulta confirmada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"content\":[{\"id\": 1,\"pacienteId\": 1,\"profissionalId\": 1,\"agendaId\": 1, \"data\": \"2025-04-07\", \"horaInicio\": \"11:01:00\", \"horaFim\": \"11:10:00\", \"status\": \"CONFIRMADA\", \"motivoConsulta\": \"gripe\"}],\"pageable\":{\"pageNumber\":0,\"pageSize\":20},\"totalElements\":1,\"totalPages\":1}"
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
    @PutMapping("/{id}/confirmar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ConsultaDTO> confirmarConsulta(
            @Parameter(description = "Id da consulta", example = "1", required = true)@PathVariable Long id) {
        ConsultaDTO consultaConfirmada = consultaService.confirmarConsulta(id);
        return ResponseEntity.ok(consultaConfirmada);
    }

    @Operation(summary = "Efetua cancelamento da consulta",
            description = "Cancela uma consulta pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Consulta cancelada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"content\":[{\"id\": 1,\"pacienteId\": 1,\"profissionalId\": 1,\"agendaId\": 1, \"data\": \"2025-04-07\", \"horaInicio\": \"11:01:00\", \"horaFim\": \"11:10:00\", \"status\": \"CANCELADA\", \"motivoConsulta\": \"gripe\"}],\"pageable\":{\"pageNumber\":0,\"pageSize\":20},\"totalElements\":1,\"totalPages\":1}"
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
    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ConsultaDTO> cancelarConsulta(
            @Parameter(description = "Id da consulta", example = "1", required = true)
            @PathVariable Long id) {
        ConsultaDTO consultaCancelada = consultaService.cancelarConsulta(id);
        return ResponseEntity.ok(consultaCancelada);
    }
}

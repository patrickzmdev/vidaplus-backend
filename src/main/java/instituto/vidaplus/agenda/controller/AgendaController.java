package instituto.vidaplus.agenda.controller;

import instituto.vidaplus.agenda.dto.AgendaDTO;
import instituto.vidaplus.agenda.service.AgendaService;
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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendas")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;


    @Operation(summary = "Criar agenda",
            description = "Cria uma nova agenda para um profissional específico")
    @ApiResponse(responseCode = "201",
            description = "Agenda criada com sucesso",
            content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AgendaDTO.class),
            examples = @ExampleObject(
                    value = "{\"id\": 1, \"profissionalId\": 1, \"ativo\": true, \"horarioDisponiveis\": [], \"consultas\": []}"
            )
    )
    )
    @ApiResponse(responseCode = "404",
            description = "Dados inválidos ou profissional não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Profissional não encontrado\"}"
                    )
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AgendaDTO> criarAgenda(
            @Parameter(description = "Id do profissional", example = "1", required = true)
            @RequestParam Long profissionalId) {
       AgendaDTO agenda = agendaService.criarAgenda(profissionalId);
        return ResponseEntity.status(HttpStatus.CREATED).body(agenda);
    }

    @Operation(summary = "Buscar agenda",
            description = "Faz a busca de uma agenda pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Agenda encontrada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"profissionalId\": 1, \"ativo\": true, \"horarioDisponiveis\": [], \"consultas\": []}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Agenda não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Agenda não encontrada\"}"
                    )
            )
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AgendaDTO> buscarAgendaPorId(
            @Parameter(description = "Id da agenda", example = "1", required = true)
            @PathVariable Long id) {
        AgendaDTO agenda = agendaService.buscarAgendaPorId(id);
        return ResponseEntity.ok(agenda);
    }

    @Operation(summary = "Deletar agenda",
            description = "Deleta uma agenda pelo ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String deletarAgenda(
            @Parameter(description = "ID da agenda", example = "1", required = true)
            @PathVariable Long id) {

        return agendaService.deletarAgenda(id);
    }

    @Operation(summary = "Buscar agenda por profissional",
            description = "Faz a busca de uma agenda pelo ID do profissional")
    @ApiResponse(responseCode = "200",
            description = "Resultado da busca",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 3, \"ativo\": true, \"profissionalId\": 1, \"nomeProfissional\": \"Médico\", \"horarioDisponiveis\": []}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Agenda não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Agenda não encontrada\"}"
                    )
            )
    )
    @GetMapping("/profissional/{profissionalId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AgendaDTO> buscarAgendaPorProfissional(
            @Parameter(description = "ID do profissional", example = "3", required = true)
            @PathVariable Long profissionalId) {
        AgendaDTO agenda = agendaService.buscarAgendaPorProfissional(profissionalId);
        return ResponseEntity.ok(agenda);
    }

    @Operation(summary = "Efetua ativacao da agenda",
            description = "Ativa uma agenda pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Agenda ativada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"profissionalId\": 1, \"ativo\": true, \"horarioDisponiveis\": [], \"consultas\": []}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Agenda não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Agenda não encontrada\"}"
                    )
            )
    )
    @PutMapping("/{id}/ativar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AgendaDTO> ativarAgenda(
            @Parameter(description = "ID da agenda", example = "1", required = true)
            @PathVariable Long id) {
        AgendaDTO agenda = agendaService.ativarAgenda(id);
        return ResponseEntity.ok(agenda);
    }

    @Operation(summary = "Efetua a desativação da agenda",
            description = "Desativa uma agenda pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Agenda desativada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AgendaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"profissionalId\": 1, \"ativo\": false, \"horarioDisponiveis\": [], \"consultas\": []}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Agenda não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Agenda não encontrada\"}"
                    )
            )
    )
    @PutMapping("/{id}/desativar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AgendaDTO> desativarAgenda(
            @Parameter(description = "ID da agenda", example = "1", required = true)
            @PathVariable Long id) {
        AgendaDTO agenda = agendaService.desativarAgenda(id);
        return ResponseEntity.ok(agenda);
    }
}

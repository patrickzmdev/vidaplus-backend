package instituto.vidaplus.paciente.controller;

import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import instituto.vidaplus.paciente.dto.PacienteAlergiaDTO;
import instituto.vidaplus.paciente.enums.AlergiaEnum;
import instituto.vidaplus.paciente.service.PacienteAlergiaService;
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
@RequestMapping("/pacientesAlergias")
@RequiredArgsConstructor
public class PacienteAlergiaController {

    private final PacienteAlergiaService pacienteAlergiaService;

    @Operation(summary = "Adicionar alergia ao paciente",
            description = "Adiciona uma alergia específica a um paciente.")
    @ApiResponse(responseCode = "201", description = "Alergia adicionada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PacienteAlergiaDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"pacienteId\": 1, \"alergia\": \"PENICILINA\", \"observacao\": \"Sem observações\"}")))
    @ApiResponse(responseCode = "400", description = "Paciente não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Paciente não encontrado\"}")))
    @PostMapping("/{pacienteId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> adicionarAlergia(
            @Parameter(description = "Id do paciente", example = "1", required = true) @PathVariable Long pacienteId,
            @Parameter(description = "Tipo de alergia", example = "ACAROS")@RequestParam AlergiaEnum alergia,
            @Parameter(description = "Observações", example = "sem observações", required = true)@RequestParam String observacao) {
        PacienteAlergiaDTO pacienteAlergia = pacienteAlergiaService.adicionarAlergia(pacienteId, alergia, observacao);
        return new ResponseEntity<>(pacienteAlergia, HttpStatus.OK);
    }

    @Operation(summary = "Listar alergia do paciente",
            description = "Lista todas as alergias de um paciente específico.")
    @ApiResponse(responseCode = "200",
            description = "Retorna a lista de alergias do paciente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PacienteAlergiaDTO.class),
                    examples = @ExampleObject(
                            value = "[{\"id\": 1, \"pacienteId\": 1, \"alergia\": \"PENICILINA\", \"observacao\": \"sem observações\"}]"
                    )
            )
    )
    @GetMapping("/{pacienteId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> listaAlergiasPaciente(
            @Parameter(description = "Id do paciente", example = "1", required = true)@PathVariable Long pacienteId) {
        PacienteAlergiaDTO pacienteAlergia = new PacienteAlergiaDTO();
        return new ResponseEntity<>(pacienteAlergiaService.listaAlergiasPaciente(pacienteId, pacienteAlergia), HttpStatus.OK);
    }

    @Operation(summary = "Excluir alergia do paciente",
            description = "Exclui uma alergia específica de um paciente.")
    @ApiResponse(responseCode = "200", description = "Alergia excluída com sucesso",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Alergia excluída com sucesso\"}")))
    @ApiResponse(responseCode = "404", description = "Alergia não encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Alergia não encontrada para o paciente\"}")))
    @DeleteMapping("/{pacienteId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> excluirAlergia(
            @Parameter(description = "Id do paciente", example = "1", required = true)@PathVariable Long pacienteId,
            @Parameter(description = "Tipo de alergia", example = "LACTOSE", required = true)@RequestParam AlergiaEnum alergia) {
        String mensagem = pacienteAlergiaService.excluirAlergia(pacienteId, alergia);
        return ResponseEntity.ok(mensagem);
    }
}

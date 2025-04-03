package instituto.vidaplus.paciente.controller;

import instituto.vidaplus.documentacao.exemplos.paciente.PacienteExamplo;
import instituto.vidaplus.exception.genericas.DadoUnicoException;
import instituto.vidaplus.paciente.dto.PacienteDTO;
import instituto.vidaplus.paciente.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(summary = "Cadastrar paciente",
            description = "Cadastra um novo paciente no sistema")
    @ApiResponse(responseCode = "201",
            description = "Paciente cadastrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PacienteDTO.class),
                    examples = @ExampleObject(
                            value = PacienteExamplo.EXEMPLO_PACIENTE_POST
                    )
            )
    )
    @ApiResponse(responseCode = "400",
            description = "CPF do paciente é obrigatório",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = PacienteExamplo.EXEMPLO_ERRO_VALIDACAO
                    )
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> cadastrarPaciente(@RequestBody PacienteDTO paciente) {
        try {
            PacienteDTO pacienteCadastrado = pacienteService.cadastrarPaciente(paciente);
            return new ResponseEntity<>(pacienteCadastrado, HttpStatus.CREATED);
        } catch (DadoUnicoException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Editar paciente",
            description = "Edita os dados de um paciente existente")
    @ApiResponse(responseCode = "200",
            description = "Paciente editado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PacienteDTO.class),
                    examples = @ExampleObject(
                            value = PacienteExamplo.EXEMPLO_PACIENTE_EDICAO
                    )
            )
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PacienteDTO> editarPaciente(@PathVariable Long id, @RequestBody PacienteDTO paciente) {
        PacienteDTO pacienteEditado = pacienteService.editarPaciente(id, paciente);
        return ResponseEntity.ok(pacienteEditado);
    }

    @Operation(summary = "Excluir paciente",
            description = "Faz a a exclusão de um paciente do sistema")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> excluirPaciente(
            @Parameter(description = "ID do paciente", example = "1", required = true)
            @PathVariable Long id) {
        String mensagem = pacienteService.excluirPaciente(id);
        return ResponseEntity.ok(mensagem);
    }

    @Operation(summary = "buscar paciente",
            description = "Faz a busca de um paciente pelo ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PacienteDTO> buscarPaciente(
            @Parameter(description = "ID do paciente", example = "1", required = true)
            @PathVariable Long id) {
        PacienteDTO paciente = pacienteService.buscarPaciente(id);
        return ResponseEntity.ok(paciente);
    }
}

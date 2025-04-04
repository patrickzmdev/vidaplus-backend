package instituto.vidaplus.historico.controller;

import instituto.vidaplus.consulta.dto.ConsultaDTO;
import instituto.vidaplus.exame.dto.ExameDTO;
import instituto.vidaplus.historico.dto.HistoricoClinicoDTO;
import instituto.vidaplus.historico.enums.TipoHistoricoEnum;
import instituto.vidaplus.historico.service.HistoricoClinicoService;
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
@RequestMapping("/historicos")
@RequiredArgsConstructor
public class HistoricoController {

    private final HistoricoClinicoService historicoClinicoService;

    @Operation(summary = "Registrar histórico",
            description = "Efetuar o registro de um histórico clínico para um paciente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de histórico clínico",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HistoricoClinicoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"tipoHistorico\": \"CIRURGIA\", \"descricao\": \"cirurgia na vista\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "201",
            description = "Histórico salvo com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HistoricoClinicoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"tipoHistorico\": \"CIRURGIA\", \"descricao\": \"cirurgia na vista\"}"
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
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoricoClinicoDTO> registrarHistorico(
            @Parameter(description = "Id do paciente", example = "1", required = true) @RequestParam Long pacienteId, @RequestBody HistoricoClinicoDTO historicoClinicoDTO) {
        HistoricoClinicoDTO historicoAdicionado = historicoClinicoService.registrarHistorico(pacienteId, historicoClinicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(historicoAdicionado);
    }

    @Operation(summary = "Buscar históricos por paciente",
            description = "Busca todos os históricos clínicos de um paciente com paginação")
    @ApiResponse(responseCode = "200",
            description = "Históricos encontrados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HistoricoClinicoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"content\":[{\"id\": 1, \"pacienteId\": 1, \"tipoHistorico\": \"CIRURGIA\", \"descricao\": \"cirurgia na vista\", \"dataRegistro\": \"2023-04-15\"}],\"pageable\":{\"pageNumber\":0,\"pageSize\":20},\"totalElements\":1,\"totalPages\":1}"
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<HistoricoClinicoDTO>> buscarHistoricoPorPaciente(
            @Parameter(description = "Id do paciente", example = "1", required = true)@PathVariable Long pacienteId, Pageable pageable) {
       Page<HistoricoClinicoDTO> historicos = historicoClinicoService.buscarHistoricoPorPaciente(pacienteId, pageable);
       return ResponseEntity.ok(historicos);
    }

    @Operation(summary = "Buscar históricos por paciente e tipo",
            description = "Busca históricos clínicos de um paciente filtrados por tipo específico")
    @ApiResponse(responseCode = "200",
            description = "Históricos encontrados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HistoricoClinicoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"content\":[{\"id\": 1, \"pacienteId\": 1, \"tipoHistorico\": \"CIRURGIA\", \"descricao\": \"cirurgia na vista\", \"dataRegistro\": \"2023-04-15\"}],\"pageable\":{\"pageNumber\":0,\"pageSize\":20},\"totalElements\":1,\"totalPages\":1}"
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
    @GetMapping("/paciente/{pacienteId}/tipo/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<HistoricoClinicoDTO>> buscarHistoricoPorPacienteETipo(
            @Parameter(description = "Id do paciente", example = "1", required = true)@PathVariable Long pacienteId,
            @Parameter(description = "Tipo de histórico", example = "CIRURGIA", required = true)@PathVariable TipoHistoricoEnum tipo, Pageable pageable) {
        Page<HistoricoClinicoDTO> historicos = historicoClinicoService.buscarHistoricoPorPacienteETipo(pacienteId, tipo, pageable);
        return ResponseEntity.ok(historicos);
    }

}

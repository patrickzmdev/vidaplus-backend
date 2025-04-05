package instituto.vidaplus.prontuario.controller;

import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import instituto.vidaplus.prontuario.service.ProntuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/prontuarios")
@RequiredArgsConstructor
public class ProntuarioController {

    private final ProntuarioService prontuarioService;

    @Operation(summary = "Criar prontuario",
            description = "Cria um novo prontuario associado a uma consulta")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do prontuario",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProntuarioDTO.class),
                    examples = @ExampleObject(
                            value = "{\"descricao\": \"paciente esta melhorando\", \"diagnostico\": \"virose\", \"tratamentoIndicado\": \"soro e repouso\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "201",
            description = "Prontuario criado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProntuarioDTO.class),
                    examples = @ExampleObject(
                            value = "{\"descricao\": \"paciente esta melhorando\", \"diagnostico\": \"virose\", \"tratamentoIndicado\": \"soro e repouso\"}"
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
    public ResponseEntity<ProntuarioDTO> criarProntuario(
            @Parameter(description = "Id da consulta", example = "1", required = true) @RequestParam Long consultaId,
            @RequestBody ProntuarioDTO prontuarioDTO) {
        ProntuarioDTO prontuarioAdicionado = prontuarioService.criarProntuario(consultaId, prontuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(prontuarioAdicionado);
    }

    @Operation(summary = "Buscar prontuario por ID",
            description = "Busca um prontuario pelo seu ID")
    @ApiResponse(responseCode = "200",
            description = "Prontuario encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProntuarioDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"pacienteId\": 1, \"profissionalId\": 1, \"consultaId\": 1, \"dataRegistro\": \"2025-04-04\",\"descricao\": \"paciente esta melhorando\", \"diagnostico\": \"virose\", \"tratamentoIndicado\": \"soro e repouso\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Prontuário não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Prontuário não encontrado\"}"
                    )
            )
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProntuarioDTO> buscarProntuarioPorId(
            @Parameter (description = "Id do prontuario")@PathVariable Long id) {
        ProntuarioDTO prontuarioBuscado = prontuarioService.buscarProntuarioPorId(id);
        return ResponseEntity.ok(prontuarioBuscado);
    }


    @Operation(summary = "Atualizar prontuário",
            description = "Atualiza os dados de um prontuário existente.")
    @ApiResponse(responseCode = "200", description = "Prontuário atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProntuarioDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"pacienteId\": 1, \"profissionalId\": 1, \"consultaId\": 1, \"dataRegistro\": \"2025-04-04\", \"descricao\": \"paciente está melhorando\", \"diagnostico\": \"virose\", \"tratamentoIndicado\": \"soro e repouso\"}")))
    @ApiResponse(responseCode = "404", description = "Prontuário não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Prontuário não encontrado\"}")))
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProntuarioDTO> atualizarProntuario(
            @Parameter(description = "Id do prontuário", example = "1", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do prontuário a serem atualizados", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProntuarioDTO.class),
                            examples = @ExampleObject(value = "{\"descricao\": \"paciente está melhorando\", \"diagnostico\": \"virose\", \"tratamentoIndicado\": \"soro e repouso\"}"))) @RequestBody ProntuarioDTO prontuarioDTO) {
        ProntuarioDTO prontuarioAtualizado = prontuarioService.atualizarProntuario(id, prontuarioDTO);
        return ResponseEntity.ok(prontuarioAtualizado);
    }

    @Operation(summary = "Buscar prontuario por paciente",
            description = "Busca um prontuario pelo ID do paciente")
    @ApiResponse(responseCode = "200",
            description = "Prontuario encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProntuarioDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"pacienteId\": 1, \"profissionalId\": 1, \"consultaId\": 1, \"dataRegistro\": \"2025-04-04\", \"descricao\": \"paciente está melhorando\", \"diagnostico\": \"virose\", \"tratamentoIndicado\": \"soro e repouso\"}"
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
    public ResponseEntity<Page<ProntuarioDTO>> buscarProntuariosPorPacienteId(
            @Parameter(description = "Id do paciente", example = "1", required = true)@PathVariable Long pacienteId,
            Pageable pageable) {
        Page<ProntuarioDTO> prontuarios = prontuarioService.buscarProntuariosPorPacienteId(pacienteId, pageable);
        return ResponseEntity.ok(prontuarios);
    }

    @Operation(summary = "Buscar prontuario por profissional",
            description = "Busca um prontuario pelo ID do profissional")
    @ApiResponse(responseCode = "200",
            description = "Prontuario encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProntuarioDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"pacienteId\": 1, \"profissionalId\": 1, \"consultaId\": 1, \"dataRegistro\": \"2025-04-04\", \"descricao\": \"paciente está melhorando\", \"diagnostico\": \"virose\", \"tratamentoIndicado\": \"soro e repouso\"}"
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
    public ResponseEntity<Page<ProntuarioDTO>> buscarProntuariosPorProfissionalId(
            @Parameter(description = "Id do profissional", example = "1", required = true)@PathVariable Long profissionalId, Pageable pageable) {
        Page<ProntuarioDTO> prontuarios = prontuarioService.buscarProntuariosPorProfissionalId(profissionalId, pageable);
        return ResponseEntity.ok(prontuarios);
    }

    @Operation(summary = "Buscar prontuario por consulta",
            description = "Busca um prontuario pelo ID da consulta")
    @ApiResponse(responseCode = "200",
            description = "Consulta encontrada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProntuarioDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"pacienteId\": 1, \"profissionalId\": 1, \"consultaId\": 1, \"dataRegistro\": \"2025-04-04\", \"descricao\": \"paciente está melhorando\", \"diagnostico\": \"virose\", \"tratamentoIndicado\": \"soro e repouso\"}"
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
    @Parameter(name = "sort", description = "Ordenação: propriedade,direção (ex: id,asc)", schema = @Schema(type = "string", defaultValue = "id,asc"))
    @GetMapping("/consulta/{consultaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProntuarioDTO> buscarProntuarioPorConsultaId(
            @Parameter(description = "Id da consulta", example = "1", required = true)@PathVariable Long consultaId) {
        ProntuarioDTO prontuario = prontuarioService.buscarProntuarioPorConsultaId(consultaId);
        return ResponseEntity.ok(prontuario);
    }

    @Operation(summary = "Buscar prontuários por período",
            description = "Busca prontuários de um paciente em um período específico.")
    @ApiResponse(responseCode = "200", description = "Prontuários encontrados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProntuarioDTO.class),
                    examples = @ExampleObject(value = "[{\"id\": 1, \"pacienteId\": 1, \"profissionalId\": 1, \"consultaId\": 1, \"dataRegistro\": \"2025-04-04\", \"descricao\": \"paciente está melhorando\", \"diagnostico\": \"virose\", \"tratamentoIndicado\": \"soro e repouso\"}]")))
    @GetMapping("/periodo")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ProntuarioDTO>> buscarProntuariosPorPeriodo(
            @Parameter(description = "Id do paciente", example = "1", required = true)@RequestParam Long pacienteId,
            @Parameter(description = "Data de início do período",example = "2025-05-01", required = true)@RequestParam LocalDate dataInicio,
            @Parameter(description = "Data de fim do período", example = "2025-05-25", required = true)@RequestParam LocalDate dataFim, Pageable pageable) {
        Page<ProntuarioDTO> prontuarios = prontuarioService.buscarProntuariosPorPeriodo(pacienteId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(prontuarios);
    }

    @Operation(summary = "Gerar relatório de prontuário",
            description = "Gera um relatório em PDF de um prontuário específico.")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso",
            content = @Content(mediaType = "application/pdf",
                    examples = @ExampleObject(value = "Relatório em PDF gerado com sucesso.")))
    @ApiResponse(responseCode = "404", description = "Prontuário não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Prontuário não encontrado\"}")))
    @GetMapping("/{id}/relatorio")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<byte[]> gerarRelatorio(@PathVariable Long id) {
        byte[] relatorio = prontuarioService.gerarRelatorio(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "relatorio.pdf");
        return new ResponseEntity<>(relatorio, headers, HttpStatus.OK);
    }

}

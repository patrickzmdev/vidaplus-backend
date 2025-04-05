package instituto.vidaplus.leito.controller;

import instituto.vidaplus.exame.dto.ExameDTO;
import instituto.vidaplus.leito.dto.LeitoDTO;
import instituto.vidaplus.leito.service.LeitoService;
import instituto.vidaplus.paciente.dto.PacienteDTO;
import instituto.vidaplus.paciente.dto.PacienteResumidoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leitos")
public class LeitoController {

    private final LeitoService leitoService;

    @Operation(summary = "Adicionar um novo leito",
            description = "Registra um novo leito em uma unidade hospitalar.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do leito",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LeitoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"numero\": 2}"
                    )
            )
    )
    @ApiResponse(responseCode = "201",
            description = "Leito criado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LeitoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"numero\": 2}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Unidade hospitalar não encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"message\": \"Unidade hospitalar não encontrada\"}"
                    )
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeitoDTO> adicionarLeito(
            @Parameter(description = "Id da unidade hospitalar", example = "1", required = true) @RequestParam Long unidadeHospitalarId,
            @RequestBody LeitoDTO leitoDTO) {
        LeitoDTO leitoAdicionado = leitoService.adicionarLeito(unidadeHospitalarId, leitoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(leitoAdicionado);
    }

    @Operation(summary = "Buscar leito por ID",
            description = "Faça uma busca de um leito específico pelo ID.")
    @ApiResponse(responseCode = "200",
            description = "Leito encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LeitoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1,\"numero\": 1,\"ocupado\": false,\"unidadeHospitalarId\": 1}"
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
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<LeitoDTO> buscarLeito(
            @Parameter(description = "Id do leito", example = "1", required = true)@PathVariable Long id) {
        LeitoDTO leitoBuscado = leitoService.buscarLeito(id);
        return ResponseEntity.ok(leitoBuscado);
    }

    @Operation(summary = "Deletar leito",
            description = "Deleta uma leito pelo ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletarLeito(@Parameter(description = "Id do leito", example = "1", required = true)@PathVariable Long id) {
        return leitoService.deletarLeito(id);
    }

    @Operation(summary = "Verificar leito disponível",
            description = "Verifica se um leito está disponível.")
    @ApiResponse(responseCode = "200",
            description = "Leito disponível",
            content = @Content(
                    mediaType = "text/plain",
                    examples = @ExampleObject(value = "true")
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Leito não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Leito não encontrado\"}")
            )
    )
    @GetMapping("/disponivel/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Boolean verificarLeitoDisponivel(
            @Parameter(description = "Id do leito", example = "1", required = true)@PathVariable Long id) {
        return leitoService.verificarLeitoDisponivel(id);
    }

    @Operation(summary = "Buscar paciente internado por leito",
            description = "Busca o paciente internado em um leito específico em uma unidade hospitalar.")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PacienteResumidoDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"nome\": \"João da Silva\", \"cep\": \"12345-678\", \"logradouro\": \"rua A\", \"bairro\": \"Centro\", \"cidade\": \"São Paulo\", \"possuiPlanoDeSaude\": true}")))
    @ApiResponse(responseCode = "404", description = "Leito ou unidade hospitalar não encontrados",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Unidade Hospitalar não encontrada\"}")))
    @GetMapping("/paciente/{leitoId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PacienteResumidoDTO> buscarPacienteInternadoPorLeito(
            @Parameter(description = "Id do leito", example = "1", required = true)@PathVariable Long leitoId,
            @Parameter(description = "Id da unidade hospitalar", example = "1", required = true)@RequestParam Long unidadeHospitalarId) {
        PacienteResumidoDTO pacienteInternado = leitoService.buscarPacienteInternadoPorLeito(leitoId, unidadeHospitalarId);
        return ResponseEntity.ok(pacienteInternado);
    }

    @Operation(summary = "Listar leitos disponíveis",
            description = "Lista todos os leitos disponíveis em uma unidade hospitalar específica.")
    @ApiResponse(responseCode = "200", description = "Leitos disponíveis listados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LeitoDTO.class),
                    examples = @ExampleObject(value = "[{\"id\": 1, \"numero\": 1, \"ocupado\": false, \"unidadeHospitalarId\": 1}]")))
    @ApiResponse(responseCode = "404", description = "Unidade hospitalar não encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Unidade hospitalar não encontrada\"}")))
    @GetMapping("/leitos-disponiveis/{unidadeHospitalarId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<LeitoDTO>> listarLeitosDisponiveis(
            @Parameter(description = "Id da unidade hospitalar", example = "1", required = true)@PathVariable Long unidadeHospitalarId) {
        Page<LeitoDTO> leitosDisponiveis = leitoService.listarLeitosDisponiveis(unidadeHospitalarId);
        return ResponseEntity.ok(leitosDisponiveis);
    }
}

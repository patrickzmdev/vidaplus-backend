package instituto.vidaplus.profissional.controller;

import instituto.vidaplus.documentacao.exemplos.paciente.PacienteExamplo;
import instituto.vidaplus.documentacao.exemplos.profissionais.ProfissionalExemplo;
import instituto.vidaplus.exception.genericas.DadoUnicoException;
import instituto.vidaplus.paciente.dto.PacienteDTO;
import instituto.vidaplus.profissional.dto.ProfissionalDTO;
import instituto.vidaplus.profissional.dto.ProfissionalResumoDTO;
import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
import instituto.vidaplus.profissional.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profissionais")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @Operation(summary = "Cadastrar profissional",
            description = "Cadastra um novo profissional no sistema")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PacienteDTO.class),
                    examples = @ExampleObject(
                            value = ProfissionalExemplo.EXEMPLO_MEDICO_POST
                    )
            )
    )
    @ApiResponse(responseCode = "400",
            description = "Nome do profissional é obrigatório",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = ProfissionalExemplo.EXEMPLO_ERRO_VALIDACAO
                    )
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> cadastrarProfissional(@RequestBody ProfissionalDTO profissional) {
        try {
            ProfissionalDTO profissionalCadastrado = profissionalService.cadastrarProfissional(profissional);
            return new ResponseEntity<>(profissionalCadastrado, HttpStatus.CREATED);
        } catch (DadoUnicoException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Editar profissional",
            description = "Edita os dados de um profissional existente")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PacienteDTO.class),
                    examples = @ExampleObject(
                            value = ProfissionalExemplo.EXEMPLO_MEDICO_EDICAO
                    )
            )
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProfissionalDTO> editarProfissional(@PathVariable Long id, @RequestBody ProfissionalDTO profissional) {
        ProfissionalDTO profissionalEditado = profissionalService.editarProfissional(id, profissional);
        return ResponseEntity.ok(profissionalEditado);
    }

    @Operation(summary = "Excluir profissional",
            description = "Faz a a exclusão de um profissional do sistema")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> excluirProfissional(@PathVariable Long id) {
        String mensagem = profissionalService.excluirProfissional(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProfissionalDTO> buscarProfissional(
            @Parameter(description = "Id do profissional", example = "1", required = true)
            @PathVariable Long id) {
        ProfissionalDTO profissional = profissionalService.buscarProfissional(id);
        return ResponseEntity.ok(profissional);
    }

    @Operation(summary = "Buscar profissionais por nome",
            description = "Retorna uma lista paginada de profissionais filtrados por nome")
    @GetMapping("/nome")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarProfissionaisPorNome(
            @Parameter(description = "Nome do profissional", example = "Carlos", required = true)@RequestParam String nome, Pageable pageable) {
        Page<ProfissionalResumoDTO> profissionais = profissionalService.buscarProfissionaisPorNome(nome, pageable);

        return ResponseEntity.ok(profissionais);
    }

    @Operation(summary = "Buscar profissionais por especialidade",
            description = "Retorna uma lista paginada de profissionais filtrados por especialidade")
    @GetMapping("/especialidade")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarProfissionaisPorEspecialidade(
            @Parameter(description = "Nome da especialidade", required = true, schema = @Schema(implementation = EspecialidadeEnum.class))
            @RequestParam EspecialidadeEnum especialidade, Pageable pageable) {
        Page<ProfissionalResumoDTO> profissionais = profissionalService.buscarProfissionaisPorEspecialidade(especialidade, pageable);
        return ResponseEntity.ok(profissionais);
    }

    @Operation(summary = "Buscar profissionais por cidade",
            description = "Retorna uma lista paginada de profissionais filtrados por cidade")
    @GetMapping("/cidade")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarProfissionaisPorCidade(
            @Parameter(description = "Nome da cidade",example = "Tupã", required = true)
            @RequestParam String cidade, Pageable pageable) {
        Page<ProfissionalResumoDTO> profissionais = profissionalService.buscarProfissionaisPorCidade(cidade, pageable);

        return ResponseEntity.ok(profissionais);
    }

    @Operation(summary = "Buscar profissionais por cidade e especialidade",
            description = "Retorna uma lista paginada de profissionais filtrados por cidade e especialidade")
    @GetMapping("/especialidade-cidade")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarProfissionaisPorEspecialidadeCidade(
            @Parameter(description = "Cidade e especialidade", example = "Tupã", required = true, schema = @Schema(implementation = EspecialidadeEnum.class))
            @RequestParam EspecialidadeEnum especialidade, @RequestParam String cidade, Pageable pageable) {
        Page<ProfissionalResumoDTO> profissionais = profissionalService.buscarProfissionaisPorEspecialidadeCidade(especialidade, cidade, pageable);

        return ResponseEntity.ok(profissionais);
    }
}

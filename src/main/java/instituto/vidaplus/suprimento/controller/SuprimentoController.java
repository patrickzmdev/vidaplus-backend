package instituto.vidaplus.suprimento.controller;

import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import instituto.vidaplus.suprimento.dto.SuprimentoDTO;
import instituto.vidaplus.suprimento.service.SuprimentoService;
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
@RequiredArgsConstructor
@RequestMapping("/suprimentos")
public class SuprimentoController {

    private final SuprimentoService suprimentoService;

    @Operation(summary = "Cadastrar Suprimento",
            description = "Cadastra um novo suprimento")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Informações do suprimento",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuprimentoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"nome\": \"seringas\", \"quantidade\": 1000, \"unidadeMedida\": \"pecas\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "201",
            description = "Suprimento cadastrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuprimentoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"nome\": \"seringas\", \"quantidade\": 1000, \"unidadeMedida\": \"pecas\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Unidade de medida não informada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Unidade de medida inválida\"}"
                    )
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SuprimentoDTO> cadastrarSuprimento(@RequestBody SuprimentoDTO suprimentoDTO) {
        SuprimentoDTO suprimentoCadastrado = suprimentoService.cadastrarSuprimento(suprimentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(suprimentoCadastrado);
    }

    @Operation(summary = "Editar Suprimento",
            description = "Faz a edição de um suprimento pelo ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Informações do suprimento",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuprimentoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"nome\": \"seringas\", \"quantidade\": 1000, \"unidadeMedida\": \"pecas\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Suprimento editado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SuprimentoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"nome\": \"seringas\", \"quantidade\": 1000, \"unidadeMedida\": \"pecas\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404", description = "Suprimento não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Suprimento não encontrado\"}")))
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SuprimentoDTO> editarSuprimento(
            @Parameter(description = "Id do suprimento") @PathVariable Long id, @RequestBody SuprimentoDTO suprimentoDTO) {
        SuprimentoDTO suprimentoEditado = suprimentoService.editarSuprimento(id, suprimentoDTO);
        return ResponseEntity.ok(suprimentoEditado);
    }

    @Operation(summary = "Excluir Suprimento",
            description = "Exclui um suprimento pelo ID")
    @ApiResponse(responseCode = "200", description = "Suprimento excluído com sucesso",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Suprimento excluído com sucesso\"}")))
    @ApiResponse(responseCode = "404", description = "Suprimento não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Suprimento não encontrado\"}")))
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> excluirSuprimento(
            @Parameter(description = "Id do suprimento", example = "1", required = true)@PathVariable Long id) {
        String mensagem = suprimentoService.excluirSuprimento(id);
        return ResponseEntity.ok(mensagem);
    }

    @Operation(summary = "Buscar Suprimento",
            description = "Busca um suprimento pelo ID")
    @ApiResponse(responseCode = "200", description = "Suprimento encontrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SuprimentoDTO.class),
            examples = @ExampleObject(
                            value = "{\"id\": 1, \"nome\": \"seringas\", \"quantidade\": 1000, \"unidadeMedida\": \"pecas\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404", description = "Suprimento não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Suprimento não encontrado\"}")))
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SuprimentoDTO> buscarSuprimento(
            @Parameter(description = "Id do suprimento", example = "1", required = true)@PathVariable Long id) {
        SuprimentoDTO suprimento = suprimentoService.buscarSuprimento(id);
        return ResponseEntity.ok(suprimento);
    }

    @Operation(summary = "Listar Suprimentos",
            description = "Lista todos os suprimentos com paginação")
    @ApiResponse(responseCode = "200", description = "Lista de suprimentos retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<SuprimentoDTO> listarSuprimentos(
            @Parameter(description = "Informações de paginação")Pageable pageable) {
        return suprimentoService.listarSuprimentos(pageable);
    }

    @Operation(summary = "Buscar suprimentos por nome",
            description = "Busca suprimentos pelo nome")
    @ApiResponse(responseCode = "200",
            description = "Suprimentos encontrados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuprimentoDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"nome\": \"seringas\", \"quantidade\": 1000, \"unidadeMedida\": \"pecas\"}"
                    )
            )
    )
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Tamanho da página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Ordenação: propriedade,direção (ex: id,asc)", schema = @Schema(type = "string", defaultValue = "id,asc"))
    @GetMapping("/nome")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<SuprimentoDTO> buscarSuprimentosPorNome(
            @Parameter(description = "Nome do suprimento", example = "seringa", required = true)@RequestParam String nome, Pageable pageable) {
        return suprimentoService.buscarSuprimentosPorNome(nome, pageable);
    }

}

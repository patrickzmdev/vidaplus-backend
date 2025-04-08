package instituto.vidaplus.receita.controller;

import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import instituto.vidaplus.receita.dto.ItemReceitaDTO;
import instituto.vidaplus.receita.dto.ItemReceitaInfo;
import instituto.vidaplus.receita.service.ItemReceitaService;
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

import java.util.List;

@RestController
@RequestMapping("/itens-receita")
@RequiredArgsConstructor
public class ItemReceitaController {

    private final ItemReceitaService itemReceitaService;

    @Operation(summary = "Criar Item na Receita",
            description = "Cria um novo item na receita associada a uma consulta")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do item da receita",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ItemReceitaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"receitaId\": 1, \"nome\": \"dramin\", \"controlado\": \"true\", \"quantidade\": 2}"
                    )
            )
    )
    @ApiResponse(responseCode = "201",
            description = "Item receita criado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ItemReceitaDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"receitaId\": 1, \"nome\": \"dramin\", \"controlado\": \"true\", \"quantidade\": 2}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Receita não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Receita não encontrada\"}"
                    )
            )
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ItemReceitaDTO> adicionarItemReceita(
            @Parameter(description = "Id da receita", example = "1", required = true) @RequestParam Long receitaId, @RequestBody ItemReceitaDTO itemReceitaDTO) {
        ItemReceitaDTO itemReceitaAdicionado = itemReceitaService.adicionarItemReceita(receitaId, itemReceitaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemReceitaAdicionado);
    }

    @Operation(summary = "Remover item da receita",
            description = "Remove um item específico de uma receita")
    @ApiResponse(responseCode = "200", description = "Item removido com sucesso",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Item de receita removido com sucesso\"}")))
    @ApiResponse(responseCode = "404", description = "Item de receita não encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Item de receita não encontrado\"}")))
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> removerItemReceita(
            @Parameter(description = "Id do item", example = "1", required = true)@PathVariable Long id) {
        String mensagem = itemReceitaService.removerItemReceita(id);
        return ResponseEntity.ok(mensagem);
    }

    @Operation(summary = "Listar itens por receita",
            description = "Lista todos os itens de uma receita específica")
    @ApiResponse(responseCode = "200", description = "Itens listados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ItemReceitaInfo.class),
                    examples = @ExampleObject(value = "[\"nome\": \"dramin\",\"quantidade\": 2}]")))
    @ApiResponse(responseCode = "404", description = "Receita não encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"mensagem\": \"Receita não encontrada\"}")))
    @GetMapping("/receita/{receitaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ItemReceitaInfo>> listarItemPorReceita(
            @Parameter(description = "Id da receita", example = "1", required = true)@PathVariable Long receitaId) {
        List<ItemReceitaInfo> itens = itemReceitaService.listarItemPorReceita(receitaId);
        return ResponseEntity.ok(itens);
    }

}

package instituto.vidaplus.receita.controller;

import instituto.vidaplus.receita.dto.ItemReceitaDTO;
import instituto.vidaplus.receita.dto.ItemReceitaInfo;
import instituto.vidaplus.receita.service.ItemReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-receita")
@RequiredArgsConstructor
public class ItemReceitaController {

    private final ItemReceitaService itemReceitaService;

    @PostMapping
    public ResponseEntity<ItemReceitaDTO> adicionarItemReceita(@RequestParam Long receitaId, @RequestBody ItemReceitaDTO itemReceitaDTO) {
        ItemReceitaDTO itemReceitaAdicionado = itemReceitaService.adicionarItemReceita(receitaId, itemReceitaDTO);
        return ResponseEntity.ok(itemReceitaAdicionado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerItemReceita(@PathVariable Long id) {
        String mensagem = itemReceitaService.removerItemReceita(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/receita/{receitaId}")
    public ResponseEntity<List<ItemReceitaInfo>> listarItemPorReceita(@PathVariable Long receitaId) {
        List<ItemReceitaInfo> itens = itemReceitaService.listarItemPorReceita(receitaId);
        return ResponseEntity.ok(itens);
    }

}

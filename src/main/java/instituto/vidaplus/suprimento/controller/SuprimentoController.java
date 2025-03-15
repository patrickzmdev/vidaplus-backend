package instituto.vidaplus.suprimento.controller;

import instituto.vidaplus.suprimento.dto.SuprimentoDTO;
import instituto.vidaplus.suprimento.service.SuprimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suprimentos")
public class SuprimentoController {

    private final SuprimentoService suprimentoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SuprimentoDTO> cadastrarSuprimento(@RequestParam Long administradorId,@RequestBody SuprimentoDTO suprimentoDTO) {
        SuprimentoDTO suprimentoCadastrado = suprimentoService.cadastrarSuprimento(administradorId, suprimentoDTO);
        return ResponseEntity.ok(suprimentoCadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuprimentoDTO> editarSuprimento(@PathVariable Long id,@RequestBody SuprimentoDTO suprimentoDTO) {
        SuprimentoDTO suprimentoEditado = suprimentoService.editarSuprimento(id, suprimentoDTO);
        return ResponseEntity.ok(suprimentoEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirSuprimento(@PathVariable Long id) {
        String mensagem = suprimentoService.excluirSuprimento(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuprimentoDTO> buscarSuprimento(@PathVariable Long id) {
        SuprimentoDTO suprimento = suprimentoService.buscarSuprimento(id);
        return ResponseEntity.ok(suprimento);
    }

    @GetMapping
    public Page<SuprimentoDTO> listarSuprimentos(Pageable pageable) {
        return suprimentoService.listarSuprimentos(pageable);
    }

    @GetMapping("/nome")
    public Page<SuprimentoDTO> buscarSuprimentosPorNome(@RequestParam String nome, Pageable pageable) {
        return suprimentoService.buscarSuprimentosPorNome(nome, pageable);
    }

}

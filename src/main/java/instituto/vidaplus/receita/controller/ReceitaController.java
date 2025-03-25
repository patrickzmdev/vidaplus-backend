package instituto.vidaplus.receita.controller;

import instituto.vidaplus.receita.dto.ReceitaDTO;
import instituto.vidaplus.receita.service.ReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;

    @PostMapping
    public ResponseEntity<ReceitaDTO> criarReceita(@RequestParam Long consultaId) {
        ReceitaDTO receitaAdicionada = receitaService.criarReceita(consultaId);
        return ResponseEntity.ok(receitaAdicionada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTO> buscarReceitaPorId(@PathVariable Long id) {
        ReceitaDTO receitaBuscada = receitaService.buscarReceitaPorId(id);
        return ResponseEntity.ok(receitaBuscada);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<Page<ReceitaDTO>> buscarReceitasPorPacienteId(@PathVariable Long pacienteId, Pageable pageable) {
        Page<ReceitaDTO> receitas = receitaService.buscarReceitasPorPacienteId(pacienteId, pageable);
        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<Page<ReceitaDTO>> buscarReceitasPorProfissionalId(@PathVariable Long profissionalId, Pageable pageable) {
        Page<ReceitaDTO> receitas = receitaService.buscarReceitasPorProfissionalId(profissionalId, pageable);
        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<Page<ReceitaDTO>> buscarReceitaPorConsultaId(@PathVariable Long consultaId, Pageable pageable) {
        Page<ReceitaDTO> receitas = receitaService.buscarReceitaPorConsultaId(consultaId, pageable);
        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/periodo")
    public ResponseEntity<Page<ReceitaDTO>> buscarReceitasPorPeriodo(@RequestParam Long pacienteId, @RequestParam LocalDate dataInicio, @RequestParam LocalDate dataFim, Pageable pageable) {
        Page<ReceitaDTO> receitas = receitaService.buscarReceitasPorPeriodo(pacienteId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/{id}/relatorio")
    public ResponseEntity<byte[]> gerarRelatorio(@PathVariable Long id) {
        byte[] relatorio = receitaService.gerarRelatorio(id);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=relatorio-receita.pdf")
                .body(relatorio);
    }
}

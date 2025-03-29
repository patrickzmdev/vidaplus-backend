package instituto.vidaplus.exame.controller;

import instituto.vidaplus.exame.dto.ExameDTO;
import instituto.vidaplus.exame.dto.ExameSuprimentoDTO;
import instituto.vidaplus.exame.service.ExameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExameController {

    private final ExameService exameService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ExameDTO> agendarExame(@RequestParam Long pacienteId, @RequestBody ExameDTO exameDTO) {
        ExameDTO exameAgendado = exameService.agendarExame(pacienteId, exameDTO);
        return ResponseEntity.ok(exameAgendado);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ExameDTO> buscarExame(@PathVariable Long id) {
        ExameDTO exame = exameService.buscarExame(id);
        return ResponseEntity.ok(exame);
    }

    @GetMapping("/paciente/{pacienteId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ExameDTO>> buscarExamesPorPaciente(@PathVariable Long pacienteId, Pageable pageable) {
        Page<ExameDTO> exames = exameService.buscarExamesPorPaciente(pacienteId, pageable);
        return ResponseEntity.ok(exames);
    }

    @PutMapping("/finalizar/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String finalizarExame(@PathVariable Long id) {
        return exameService.finalizarExame(id);
    }

    @PutMapping("/cancelar/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String cancelarExame(@PathVariable Long id) {
        return exameService.cancelarExame(id);
    }

    @PostMapping("/suprimento/{exameId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ExameSuprimentoDTO> adicionarSuprimentoAExame(@PathVariable Long exameId, @RequestParam Long suprimentoId, @RequestParam Integer quantidadeUtilizada) {
        ExameSuprimentoDTO exameSuprimento = exameService.adicionarSuprimentoAExame(exameId, suprimentoId, quantidadeUtilizada);
        return ResponseEntity.ok(exameSuprimento);
    }
}

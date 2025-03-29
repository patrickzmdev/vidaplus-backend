package instituto.vidaplus.prontuario.controller;

import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import instituto.vidaplus.prontuario.service.ProntuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/prontuarios")
@RequiredArgsConstructor
public class ProntuarioController {

    private final ProntuarioService prontuarioService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProntuarioDTO> criarProntuario(@RequestParam Long consultaId,@RequestBody ProntuarioDTO prontuarioDTO) {
        ProntuarioDTO prontuarioAdicionado = prontuarioService.criarProntuario(consultaId, prontuarioDTO);
        return ResponseEntity.ok(prontuarioAdicionado);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProntuarioDTO> buscarProntuarioPorId(@PathVariable Long id) {
        ProntuarioDTO prontuarioBuscado = prontuarioService.buscarProntuarioPorId(id);
        return ResponseEntity.ok(prontuarioBuscado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProntuarioDTO> atualizarProntuario(@PathVariable Long id, @RequestBody ProntuarioDTO prontuarioDTO) {
        ProntuarioDTO prontuarioAtualizado = prontuarioService.atualizarProntuario(id, prontuarioDTO);
        return ResponseEntity.ok(prontuarioAtualizado);
    }

    @GetMapping("/paciente/{pacienteId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ProntuarioDTO>> buscarProntuariosPorPacienteId(@PathVariable Long pacienteId, Pageable pageable) {
        Page<ProntuarioDTO> prontuarios = prontuarioService.buscarProntuariosPorPacienteId(pacienteId, pageable);
        return ResponseEntity.ok(prontuarios);
    }

    @GetMapping("/profissional/{profissionalId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ProntuarioDTO>> buscarProntuariosPorProfissionalId(@PathVariable Long profissionalId, Pageable pageable) {
        Page<ProntuarioDTO> prontuarios = prontuarioService.buscarProntuariosPorProfissionalId(profissionalId, pageable);
        return ResponseEntity.ok(prontuarios);
    }

    @GetMapping("/consulta/{consultaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProntuarioDTO> buscarProntuarioPorConsultaId(@PathVariable Long consultaId) {
        ProntuarioDTO prontuario = prontuarioService.buscarProntuarioPorConsultaId(consultaId);
        return ResponseEntity.ok(prontuario);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ProntuarioDTO>> buscarProntuariosPorPeriodo(@RequestParam Long pacienteId, @RequestParam LocalDate dataInicio, @RequestParam LocalDate dataFim, Pageable pageable) {
        Page<ProntuarioDTO> prontuarios = prontuarioService.buscarProntuariosPorPeriodo(pacienteId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(prontuarios);
    }

    @GetMapping("/{id}/relatorio")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<byte[]> gerarRelatorio(@PathVariable Long id) {
        byte[] relatorio = prontuarioService.gerarRelatorio(id);
        return ResponseEntity.ok(relatorio);
    }

}

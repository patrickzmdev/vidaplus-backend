package instituto.vidaplus.leito.controller;

import instituto.vidaplus.leito.dto.LeitoDTO;
import instituto.vidaplus.leito.service.LeitoService;
import instituto.vidaplus.paciente.dto.PacienteDTO;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeitoDTO> adicionarLeito(@RequestParam Long unidadeHospitalarId, @RequestBody LeitoDTO leitoDTO) {
        LeitoDTO leitoAdicionado = leitoService.adicionarLeito(unidadeHospitalarId, leitoDTO);
        return ResponseEntity.ok(leitoAdicionado);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<LeitoDTO> buscarLeito(@PathVariable Long id) {
        LeitoDTO leitoBuscado = leitoService.buscarLeito(id);
        return ResponseEntity.ok(leitoBuscado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletarLeito(@PathVariable Long id) {
        return leitoService.deletarLeito(id);
    }

    @GetMapping("/disponivel/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Boolean verificarLeitoDisponivel(@PathVariable Long id) {
        return leitoService.verificarLeitoDisponivel(id);
    }

    @GetMapping("/paciente/{leitoId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PacienteDTO> buscarPacienteInternadoPorLeito(@PathVariable Long leitoId, @RequestParam Long unidadeHospitalarId) {
        PacienteDTO pacienteInternado = leitoService.buscarPacienteInternadoPorLeito(leitoId, unidadeHospitalarId);
        return ResponseEntity.ok(pacienteInternado);
    }

    @GetMapping("/leitos-disponiveis/{unidadeHospitalarId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<LeitoDTO>> listarLeitosDisponiveis(@PathVariable Long unidadeHospitalarId) {
        Page<LeitoDTO> leitosDisponiveis = leitoService.listarLeitosDisponiveis(unidadeHospitalarId);
        return ResponseEntity.ok(leitosDisponiveis);
    }
}

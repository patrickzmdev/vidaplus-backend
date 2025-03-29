package instituto.vidaplus.consulta.controller;

import instituto.vidaplus.consulta.dto.ConsultaDTO;
import instituto.vidaplus.consulta.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ConsultaDTO> criarConsulta(@RequestParam Long pacienteId, @RequestParam Long profissionalId, @RequestParam Long agendaId, @RequestBody ConsultaDTO consultaDTO) {
        ConsultaDTO consultaAdicionada = consultaService.criarConsulta(pacienteId, profissionalId, agendaId, consultaDTO);
        return ResponseEntity.ok(consultaAdicionada);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ConsultaDTO> buscarConsultaPorId(@PathVariable Long id) {
        ConsultaDTO consultaBuscada = consultaService.buscarConsultaPorId(id);
        return ResponseEntity.ok(consultaBuscada);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ConsultaDTO>> listarConsultas(Pageable pageable) {
        Page<ConsultaDTO> consultas = consultaService.listarConsultas(pageable);
        return ResponseEntity.ok(consultas);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> deletarConsulta(@PathVariable Long id) {
        String mensagem = consultaService.deletarConsulta(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/paciente/{pacienteId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ConsultaDTO>> buscarConsultasPorPaciente(@PathVariable Long pacienteId, Pageable pageable) {
        Page<ConsultaDTO> consultasPorPaciente = consultaService.buscarConsultasPorPaciente(pacienteId, pageable);
        return ResponseEntity.ok(consultasPorPaciente);
    }

    @GetMapping("/profissional/{profissionalId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ConsultaDTO>> buscarConsultasPorProfissional(@PathVariable Long profissionalId, Pageable pageable) {
        Page<ConsultaDTO> consultasPorProfissional = consultaService.buscarConsultasPorProfissional(profissionalId, pageable);
        return ResponseEntity.ok(consultasPorProfissional);
    }

    @PutMapping("/{id}/confirmar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ConsultaDTO> confirmarConsulta(@PathVariable Long id) {
        ConsultaDTO consultaConfirmada = consultaService.confirmarConsulta(id);
        return ResponseEntity.ok(consultaConfirmada);
    }

    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ConsultaDTO> cancelarConsulta(@PathVariable Long id) {
        ConsultaDTO consultaCancelada = consultaService.cancelarConsulta(id);
        return ResponseEntity.ok(consultaCancelada);
    }
}

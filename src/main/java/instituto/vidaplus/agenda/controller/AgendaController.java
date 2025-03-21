package instituto.vidaplus.agenda.controller;

import instituto.vidaplus.agenda.dto.AgendaDTO;
import instituto.vidaplus.agenda.service.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendas")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AgendaDTO> criarAgenda(@RequestParam Long profissionalId, AgendaDTO agendaDTO) {
       AgendaDTO agenda = agendaService.criarAgenda(profissionalId, agendaDTO);
       return ResponseEntity.ok(agenda);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaDTO> buscarAgendaPorId(@PathVariable Long id) {
        AgendaDTO agenda = agendaService.buscarAgendaPorId(id);
        return ResponseEntity.ok(agenda);
    }

    @GetMapping
    public Page<AgendaDTO> listarAgendas(Pageable pageable) {
        return agendaService.listarAgendas(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaDTO> atualizarAgenda(@PathVariable Long id, @RequestBody AgendaDTO agendaDTO) {
        AgendaDTO agenda = agendaService.atualizarAgenda(id, agendaDTO);
        return ResponseEntity.ok(agenda);
    }

    @DeleteMapping("/{id}")
    public String deletarAgenda(@PathVariable Long id) {
        return agendaService.deletarAgenda(id);
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<AgendaDTO> buscarAgendaPorProfissional(@PathVariable Long profissionalId) {
        AgendaDTO agenda = agendaService.buscarAgendaPorProfissional(profissionalId);
        return ResponseEntity.ok(agenda);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<AgendaDTO> ativarAgenda(@PathVariable Long id) {
        AgendaDTO agenda = agendaService.ativarAgenda(id);
        return ResponseEntity.ok(agenda);
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<AgendaDTO> desativarAgenda(@PathVariable Long id) {
        AgendaDTO agenda = agendaService.desativarAgenda(id);
        return ResponseEntity.ok(agenda);
    }
}

package instituto.vidaplus.telemedicina.controller;

import instituto.vidaplus.telemedicina.dto.TelemedicinaDTO;
import instituto.vidaplus.telemedicina.service.TelemedicinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telemedicina")
@RequiredArgsConstructor
public class TelemedicinaController {

    private final TelemedicinaService telemedicinaService;

    @PostMapping("/{consultaId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> criarTelemedicina(@PathVariable Long consultaId) {
        TelemedicinaDTO telemedicinaAdicionada = telemedicinaService.criarTelemedicina(consultaId);
        return ResponseEntity.ok(telemedicinaAdicionada);
    }

    @GetMapping("/{consultaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> buscarTelemedicinaPorConsultaId(@PathVariable Long consultaId) {
        TelemedicinaDTO telemedicinaBuscada = telemedicinaService.buscarTelemedicinaPorConsultaId(consultaId);
        return ResponseEntity.ok(telemedicinaBuscada);
    }

    @PutMapping("/{telemedicinaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> atualizarLinkTelemedicina(@PathVariable Long telemedicinaId) {
        TelemedicinaDTO telemedicinaAtualizada = telemedicinaService.atualizarLinkTelemedicina(telemedicinaId);
        return ResponseEntity.ok(telemedicinaAtualizada);
    }

    @DeleteMapping("/{telemedicinaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String deletarTelemedicina(@PathVariable Long telemedicinaId) {
        return telemedicinaService.deletarTelemedicina(telemedicinaId);
    }

    @GetMapping("/consulta/{consultaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Boolean consultaEstaEmTelemedicina(@PathVariable Long consultaId) {
        return telemedicinaService.consultaEstaEmTelemedicina(consultaId);
    }

    @PutMapping("/iniciar/{telemedicinaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> iniciarTelemedicina(@PathVariable Long telemedicinaId) {
        TelemedicinaDTO telemedicinaIniciada = telemedicinaService.iniciarTelemedicina(telemedicinaId);
        return ResponseEntity.ok(telemedicinaIniciada);
    }

    @PutMapping("/finalizar/{telemedicinaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TelemedicinaDTO> finalizarTelemedicina(@PathVariable Long telemedicinaId) {
        TelemedicinaDTO telemedicinaFinalizada = telemedicinaService.finalizarTelemedicina(telemedicinaId);
        return ResponseEntity.ok(telemedicinaFinalizada);
    }




}

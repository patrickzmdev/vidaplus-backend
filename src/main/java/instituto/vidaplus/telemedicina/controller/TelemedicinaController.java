package instituto.vidaplus.telemedicina.controller;

import instituto.vidaplus.telemedicina.dto.TelemedicinaDTO;
import instituto.vidaplus.telemedicina.service.TelemedicinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telemedicina")
@RequiredArgsConstructor
public class TelemedicinaController {

    private final TelemedicinaService telemedicinaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TelemedicinaDTO> criarTelemedicina(Long consultaId, String linkVideoChamada) {
        TelemedicinaDTO telemedicinaAdicionada = telemedicinaService.criarTelemedicina(consultaId, linkVideoChamada);
        return ResponseEntity.ok(telemedicinaAdicionada);
    }

    @GetMapping("/{consultaId}")
    public ResponseEntity<TelemedicinaDTO> buscarTelemedicinaPorConsultaId(@PathVariable Long consultaId) {
        TelemedicinaDTO telemedicinaBuscada = telemedicinaService.buscarTelemedicinaPorConsultaId(consultaId);
        return ResponseEntity.ok(telemedicinaBuscada);
    }

    @PutMapping("/{telemedicinaId}")
    public ResponseEntity<TelemedicinaDTO> atualizarLinkTelemedicina(@PathVariable Long telemedicinaId) {
        TelemedicinaDTO telemedicinaAtualizada = telemedicinaService.atualizarLinkTelemedicina(telemedicinaId);
        return ResponseEntity.ok(telemedicinaAtualizada);
    }

    @DeleteMapping("/{telemedicinaId}")
    public String deletarTelemedicina(@PathVariable Long telemedicinaId) {
        return telemedicinaService.deletarTelemedicina(telemedicinaId);
    }

    @GetMapping("/consulta/{consultaId}")
    public Boolean consultaEstaEmTelemedicina(@PathVariable Long consultaId) {
        return telemedicinaService.consultaEstaEmTelemedicina(consultaId);
    }

    @PutMapping("/iniciar/{telemedicinaId}")
    public ResponseEntity<TelemedicinaDTO> iniciarTelemedicina(@PathVariable Long telemedicinaId) {
        TelemedicinaDTO telemedicinaIniciada = telemedicinaService.iniciarTelemedicina(telemedicinaId);
        return ResponseEntity.ok(telemedicinaIniciada);
    }

    @PutMapping("/finalizar/{telemedicinaId}")
    public ResponseEntity<TelemedicinaDTO> finalizarTelemedicina(@PathVariable Long telemedicinaId) {
        TelemedicinaDTO telemedicinaFinalizada = telemedicinaService.finalizarTelemedicina(telemedicinaId);
        return ResponseEntity.ok(telemedicinaFinalizada);
    }




}

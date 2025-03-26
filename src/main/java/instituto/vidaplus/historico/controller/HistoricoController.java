package instituto.vidaplus.historico.controller;

import instituto.vidaplus.historico.dto.HistoricoClinicoDTO;
import instituto.vidaplus.historico.enums.TipoHistoricoEnum;
import instituto.vidaplus.historico.service.HistoricoClinicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historicos")
@RequiredArgsConstructor
public class HistoricoController {

    private final HistoricoClinicoService historicoClinicoService;

    @PostMapping
    public ResponseEntity<HistoricoClinicoDTO> registrarHistorico(@RequestParam Long pacienteId, @RequestBody HistoricoClinicoDTO historicoClinicoDTO) {
        HistoricoClinicoDTO historicoAdicionado = historicoClinicoService.registrarHistorico(pacienteId, historicoClinicoDTO);
        return ResponseEntity.ok(historicoAdicionado);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<Page<HistoricoClinicoDTO>> buscarHistoricoPorPaciente(@PathVariable Long pacienteId, Pageable pageable) {
       Page<HistoricoClinicoDTO> historicos = historicoClinicoService.buscarHistoricoPorPaciente(pacienteId, pageable);
       return ResponseEntity.ok(historicos);
    }

    @GetMapping("/paciente/{pacienteId}/tipo/{tipo}")
    public ResponseEntity<Page<HistoricoClinicoDTO>> buscarHistoricoPorPacienteETipo(@PathVariable Long pacienteId, @PathVariable TipoHistoricoEnum tipo, Pageable pageable) {
        Page<HistoricoClinicoDTO> historicos = historicoClinicoService.buscarHistoricoPorPacienteETipo(pacienteId, tipo, pageable);
        return ResponseEntity.ok(historicos);
    }

}

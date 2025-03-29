package instituto.vidaplus.paciente.controller;

import instituto.vidaplus.paciente.dto.PacienteAlergiaDTO;
import instituto.vidaplus.paciente.enums.AlergiaEnum;
import instituto.vidaplus.paciente.service.PacienteAlergiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientesAlergias")
@RequiredArgsConstructor
public class PacienteAlergiaController {

    private final PacienteAlergiaService pacienteAlergiaService;

    @PostMapping("/{pacienteId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> adicionarAlergia(@PathVariable Long pacienteId, @RequestParam AlergiaEnum alergia, @RequestParam String observacao) {
        PacienteAlergiaDTO pacienteAlergia = pacienteAlergiaService.adicionarAlergia(pacienteId, alergia, observacao);
        return new ResponseEntity<>(pacienteAlergia, HttpStatus.OK);
    }

    @GetMapping("/{pacienteId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> listaAlergiasPaciente(@PathVariable Long pacienteId) {
        PacienteAlergiaDTO pacienteAlergia = new PacienteAlergiaDTO();
        return new ResponseEntity<>(pacienteAlergiaService.listaAlergiasPaciente(pacienteId, pacienteAlergia), HttpStatus.OK);
    }

    @DeleteMapping("/{pacienteId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> excluirAlergia(@PathVariable Long pacienteId, @RequestParam AlergiaEnum alergia) {
        String mensagem = pacienteAlergiaService.excluirAlergia(pacienteId, alergia);
        return ResponseEntity.ok(mensagem);
    }
}

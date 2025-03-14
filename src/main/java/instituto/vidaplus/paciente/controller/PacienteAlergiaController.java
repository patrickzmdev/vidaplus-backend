package instituto.vidaplus.paciente.controller;

import instituto.vidaplus.paciente.dto.PacienteAlergiaDTO;
import instituto.vidaplus.paciente.enums.AlergiaEnum;
import instituto.vidaplus.paciente.service.PacienteAlergiaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientesAlergias")
public class PacienteAlergiaController {

    private final PacienteAlergiaService pacienteAlergiaService;

    public PacienteAlergiaController(PacienteAlergiaService pacienteAlergiaService) {
        this.pacienteAlergiaService = pacienteAlergiaService;
    }

    @PostMapping("/{pacienteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionarAlergia(@PathVariable Long pacienteId, @RequestParam AlergiaEnum alergia, @RequestParam String observacao) {
        PacienteAlergiaDTO pacienteAlergia = pacienteAlergiaService.adicionarAlergia(pacienteId, alergia, observacao);
        return new ResponseEntity<>(pacienteAlergia, HttpStatus.OK);
    }

    @GetMapping("/{pacienteId}")
    public ResponseEntity<?> listaAlergiasPaciente(@PathVariable Long pacienteId) {
        PacienteAlergiaDTO pacienteAlergia = new PacienteAlergiaDTO();
        return new ResponseEntity<>(pacienteAlergiaService.listaAlergiasPaciente(pacienteId, pacienteAlergia), HttpStatus.OK);
    }

    @DeleteMapping("/{pacienteId}")
    public ResponseEntity<String> excluirAlergia(@PathVariable Long pacienteId, @RequestParam AlergiaEnum alergia) {
        String mensagem = pacienteAlergiaService.excluirAlergia(pacienteId, alergia);
        return ResponseEntity.ok(mensagem);
    }
}

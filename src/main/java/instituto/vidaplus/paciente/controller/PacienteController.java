package instituto.vidaplus.paciente.controller;

import instituto.vidaplus.exception.genericas.DadoUnicoException;
import instituto.vidaplus.paciente.dto.PacienteDTO;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> cadastrarPaciente(@RequestParam Long administradorId, @RequestBody PacienteDTO paciente) {
        try {
            PacienteDTO pacienteCadastrado = pacienteService.cadastrarPaciente(administradorId, paciente);
            return new ResponseEntity<>(pacienteCadastrado, HttpStatus.OK);
        } catch (DadoUnicoException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> editarPaciente(@PathVariable Long id, @RequestBody PacienteDTO paciente) {
        PacienteDTO pacienteEditado = pacienteService.editarPaciente(id, paciente);
        return ResponseEntity.ok(pacienteEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPaciente(@PathVariable Long id) {
        String mensagem = pacienteService.excluirPaciente(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPaciente(@PathVariable Long id) {
        PacienteDTO paciente = pacienteService.buscarPaciente(id);
        return ResponseEntity.ok(paciente);
    }
}

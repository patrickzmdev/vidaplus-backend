package instituto.vidaplus.paciente.controller;

import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody Paciente paciente) {
        Paciente pacienteCadastrado = pacienteService.cadastrarPaciente(paciente);
        return new ResponseEntity<>(pacienteCadastrado, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> editarPaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        pacienteService.editarPaciente(id, paciente);
        Paciente pacienteAtualizado = pacienteService.buscarPaciente(id);
        return new ResponseEntity<>(pacienteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Paciente> excluirPaciente(@PathVariable Long id) {
        pacienteService.excluirPaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

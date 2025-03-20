package instituto.vidaplus.consulta.controller;

import instituto.vidaplus.consulta.dto.ConsultaDTO;
import instituto.vidaplus.consulta.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<ConsultaDTO> criarConsulta(@RequestBody ConsultaDTO consultaDTO) {
        ConsultaDTO consultaAdicionada = consultaService.criarConsulta(consultaDTO);
        return ResponseEntity.ok(consultaAdicionada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> buscarConsultaPorId(@PathVariable Long id) {
        ConsultaDTO consultaBuscada = consultaService.buscarConsultaPorId(id);
        return ResponseEntity.ok(consultaBuscada);
    }

    @GetMapping
    public ResponseEntity<Page<ConsultaDTO>> listarConsultas(Pageable pageable) {
        Page<ConsultaDTO> consultas = consultaService.listarConsultas(pageable);
        return ResponseEntity.ok(consultas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDTO consultaDTO) {
        ConsultaDTO consultaAtualizada = consultaService.atualizarConsulta(id, consultaDTO);
        return ResponseEntity.ok(consultaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarConsulta(@PathVariable Long id) {
        String mensagem = consultaService.deletarConsulta(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<Page<ConsultaDTO>> buscarConsultasPorPaciente(@PathVariable Long pacienteId, Pageable pageable) {
        Page<ConsultaDTO> consultasPorPaciente = consultaService.buscarConsultasPorPaciente(pacienteId, pageable);
        return ResponseEntity.ok(consultasPorPaciente);
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<Page<ConsultaDTO>> buscarConsultasPorProfissional(@PathVariable Long profissionalId, Pageable pageable) {
        Page<ConsultaDTO> consultasPorProfissional = consultaService.buscarConsultasPorProfissional(profissionalId, pageable);
        return ResponseEntity.ok(consultasPorProfissional);
    }
}

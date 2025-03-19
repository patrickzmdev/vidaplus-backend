package instituto.vidaplus.horario.controller;

import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import instituto.vidaplus.horario.service.HorarioDisponivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horarios")
@RequiredArgsConstructor
public class HorarioDisponivelController {

    private HorarioDisponivelService horarioDisponivelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HorarioDisponivelDTO> criarHorarioDisponivel(@RequestBody HorarioDisponivelDTO horarioDisponivelDTO) {
        HorarioDisponivelDTO horarioAdicionado = horarioDisponivelService.criarHorarioDisponivel(horarioDisponivelDTO);
        return ResponseEntity.ok(horarioAdicionado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioDisponivelDTO> buscarHorarioDisponivelPorId(@PathVariable Long id) {
        HorarioDisponivelDTO horarioBuscado = horarioDisponivelService.buscarHorarioDisponivelPorId(id);
        return ResponseEntity.ok(horarioBuscado);
    }

    @GetMapping
    public Page<HorarioDisponivelDTO> listarHorarios(Pageable pageable) {
        return horarioDisponivelService.listarHorarios(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioDisponivelDTO> atualizarHorarioDisponivel(@PathVariable Long id, @RequestBody HorarioDisponivelDTO horarioDisponivelDTO) {
        HorarioDisponivelDTO horarioAtualizado = horarioDisponivelService.atualizarHorarioDisponivel(id, horarioDisponivelDTO);
        return ResponseEntity.ok(horarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public String deletarHorarioDisponivel(@PathVariable Long id) {
        return horarioDisponivelService.deletarHorarioDisponivel(id);
    }

    @GetMapping("/agenda/{agendaId}")
    public ResponseEntity<List<HorarioDisponivelDTO>> listarHorariosPorAgenda(@PathVariable Long agendaId) {
        List<HorarioDisponivelDTO> horariosPorAgenda = horarioDisponivelService.listarHorariosPorAgenda(agendaId);
        return ResponseEntity.ok(horariosPorAgenda);
    }

    @GetMapping("/agenda/{agendaId}/disponiveis")
    public ResponseEntity<List<HorarioDisponivelDTO>> listarHorariosDisponiveisPorAgenda(@PathVariable Long agendaId) {
        List<HorarioDisponivelDTO> horariosDisponiveisPorAgenda = horarioDisponivelService.listarHorariosDisponiveisPorAgenda(agendaId);
        return ResponseEntity.ok(horariosDisponiveisPorAgenda);
    }

    @PutMapping("/{id}/indisponivel")
    public String marcarHorarioComoIndisponivel(@PathVariable Long id) {
        return horarioDisponivelService.marcarHorarioComoIndisponivel(id);
    }

    @PutMapping("/{id}/disponivel")
    public String marcarHorarioComoDisponivel(@PathVariable Long id) {
        return horarioDisponivelService.marcarHorarioComoDisponivel(id);
    }
}

package instituto.vidaplus.internacao.controller;

import instituto.vidaplus.internacao.dto.InternacaoDTO;
import instituto.vidaplus.internacao.dto.InternacaoSuprimentoDto;
import instituto.vidaplus.internacao.model.Internacao;
import instituto.vidaplus.internacao.service.InternacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internacoes")
public class InternacaoController {

    private final InternacaoService internacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InternacaoDTO> registrarInternacao(@RequestParam Long pacienteId, @RequestParam Long leitoId, @RequestBody Internacao internacao) {
        InternacaoDTO internacaoRegistrada = internacaoService.registrarInternacao(pacienteId, leitoId, internacao);
        return ResponseEntity.ok(internacaoRegistrada);
    }

    @PutMapping("/encerrar/{id}")
    public String encerrarInternacao(@PathVariable Long id){
        return internacaoService.encerrarInternacao(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<InternacaoDTO> transferirPaciente(@PathVariable Long id, @RequestParam Long leitoId) {
        InternacaoDTO internacaoTransferida = internacaoService.transferirPaciente(id, leitoId);
        return ResponseEntity.ok(internacaoTransferida);
    }

    @PostMapping("/suprimento/{internacaoId}")
    public ResponseEntity<InternacaoSuprimentoDto> adicionarSuprimentoAUmaInternacao(@PathVariable Long internacaoId,@RequestParam Long suprimentoId, @RequestBody Integer quantidade) {
        InternacaoSuprimentoDto internacaoSuprimento = internacaoService.adicionarSuprimentoAUmaInternacao(internacaoId, suprimentoId, quantidade);
        return ResponseEntity.ok(internacaoSuprimento);
    }


}

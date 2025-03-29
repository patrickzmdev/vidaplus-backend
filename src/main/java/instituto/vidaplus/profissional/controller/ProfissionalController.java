package instituto.vidaplus.profissional.controller;

import instituto.vidaplus.exception.genericas.DadoUnicoException;
import instituto.vidaplus.profissional.dto.ProfissionalDTO;
import instituto.vidaplus.profissional.dto.ProfissionalResumoDTO;
import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
import instituto.vidaplus.profissional.service.ProfissionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profissionais")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> cadastrarProfissional(@RequestBody ProfissionalDTO profissional) {
        try {
            ProfissionalDTO profissionalCadastrado = profissionalService.cadastrarProfissional(profissional);
            return new ResponseEntity<>(profissionalCadastrado, HttpStatus.OK);
        } catch (DadoUnicoException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProfissionalDTO> editarProfissional(@PathVariable Long id, @RequestBody ProfissionalDTO profissional) {
        ProfissionalDTO profissionalEditado = profissionalService.editarProfissional(id, profissional);
        return ResponseEntity.ok(profissionalEditado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> excluirProfissional(@PathVariable Long id) {
        String mensagem = profissionalService.excluirProfissional(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProfissionalDTO> buscarProfissional(@PathVariable Long id) {
        ProfissionalDTO profissional = profissionalService.buscarProfissional(id);
        return ResponseEntity.ok(profissional);
    }

    @GetMapping("/nome")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarProfissionaisPorNome(@RequestParam String nome, Pageable pageable) {
        Page<ProfissionalResumoDTO> profissionais = profissionalService.buscarProfissionaisPorNome(nome, pageable);

        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/especialidade")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarProfissionaisPorEspecialidade(@RequestParam EspecialidadeEnum especialidade, Pageable pageable) {
        Page<ProfissionalResumoDTO> profissionais = profissionalService.buscarProfissionaisPorEspecialidade(especialidade, pageable);

        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/cidade")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarProfissionaisPorCidade(@RequestParam String cidade, Pageable pageable) {
        Page<ProfissionalResumoDTO> profissionais = profissionalService.buscarProfissionaisPorCidade(cidade, pageable);

        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/especialidade-cidade")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarProfissionaisPorEspecialidadeCidade(@RequestParam EspecialidadeEnum especialidade, @RequestParam String cidade, Pageable pageable) {
        Page<ProfissionalResumoDTO> profissionais = profissionalService.buscarProfissionaisPorEspecialidadeCidade(especialidade, cidade, pageable);

        return ResponseEntity.ok(profissionais);
    }
}

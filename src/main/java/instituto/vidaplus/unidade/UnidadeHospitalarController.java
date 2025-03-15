package instituto.vidaplus.unidade;

import instituto.vidaplus.exception.genericas.DadoUnicoException;
import instituto.vidaplus.unidade.dto.UnidadeHospitalarDTO;
import instituto.vidaplus.unidade.service.UnidadeHospitalarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unidades-hospitalares")
@RequiredArgsConstructor
public class UnidadeHospitalarController {

    private final UnidadeHospitalarService unidadeHospitalarService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> cadastrarUnidadeHospitalar(@RequestParam Long administradorId, @RequestBody UnidadeHospitalarDTO unidadeHospitalarDTO) {
        try {
            UnidadeHospitalarDTO unidadeHospitalarCadastrada = unidadeHospitalarService.cadastrarUnidadeHospitalar(administradorId, unidadeHospitalarDTO);
            return new ResponseEntity<>(unidadeHospitalarCadastrada, HttpStatus.OK);
        } catch (DadoUnicoException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarUnidadeHospitalar(@PathVariable Long id, @RequestBody UnidadeHospitalarDTO unidadeHospitalarDTO) {
        try {
            UnidadeHospitalarDTO unidadeHospitalarEditada = unidadeHospitalarService.editarUnidadeHospitalar(id, unidadeHospitalarDTO);
            return ResponseEntity.ok(unidadeHospitalarEditada);
        }catch (DadoUnicoException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirUnidadeHospitalar(@PathVariable Long id) {
        String mensagem = unidadeHospitalarService.excluirUnidadeHospitalar(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeHospitalarDTO> buscarUnidadeHospitalar(@PathVariable Long id) {
        UnidadeHospitalarDTO unidadeHospitalar = unidadeHospitalarService.buscarUnidadeHospitalar(id);
        return ResponseEntity.ok(unidadeHospitalar);
    }

    @GetMapping("/cidade")
    public ResponseEntity<?> buscarUnidadeHospitalarPorCidade(@RequestParam String cidade, Pageable pageable) {
        Page<UnidadeHospitalarDTO> unidadesHospitalares = unidadeHospitalarService.buscarUnidadeHospitalarPorCidade(cidade, pageable);
        return ResponseEntity.ok(unidadesHospitalares);
    }
}

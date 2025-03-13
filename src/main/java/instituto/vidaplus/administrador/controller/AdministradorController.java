package instituto.vidaplus.administrador.controller;

import instituto.vidaplus.administrador.model.Administrador;
import instituto.vidaplus.administrador.service.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @PostMapping
    public ResponseEntity<Administrador> cadastrarAdministrador(@RequestBody Administrador administrador) {
        Administrador administradorCadastrado = administradorService.cadastrarAdministrador(administrador);
        return ResponseEntity.ok(administradorCadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> editarAdministrador(@PathVariable Long id, @RequestBody Administrador administrador) {
        administradorService.editarAdministrador(id, administrador);
        Administrador administradorAtualizado = administradorService.buscarAdministrador(id);
        return ResponseEntity.ok(administradorAtualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> buscarAdministradorPorId(@PathVariable Long id) {
        Administrador administrador = administradorService.buscarAdministrador(id);
        return ResponseEntity.ok(administrador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Administrador> excluirAdministrador(@PathVariable Long id) {
        administradorService.excluirAdministrador(id);
        return ResponseEntity.noContent().build();
    }
}

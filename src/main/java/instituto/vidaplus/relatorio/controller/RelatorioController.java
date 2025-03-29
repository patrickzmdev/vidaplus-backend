package instituto.vidaplus.relatorio.controller;

import instituto.vidaplus.relatorio.dto.RelatorioFinanceiroDTO;
import instituto.vidaplus.relatorio.dto.ResumoFinanceiroDTO;
import instituto.vidaplus.relatorio.service.RelatorioFinanceiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioFinanceiroService relatorioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RelatorioFinanceiroDTO> registrarRelatorio(@RequestBody RelatorioFinanceiroDTO relatorioDTO) {
        return ResponseEntity.ok(relatorioService.registrarRelatorio(relatorioDTO));
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RelatorioFinanceiroDTO>> buscarRelatorioPorPeriodo(@RequestParam LocalDate inicio,@RequestParam LocalDate fim) {
        return ResponseEntity.ok(relatorioService.buscarRelatorioPorPeriodo(inicio, fim));
    }

    @GetMapping("/resumo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResumoFinanceiroDTO> gerarResumoFinanceiro(@RequestParam LocalDate inicio,@RequestParam LocalDate fim) {
        return ResponseEntity.ok(relatorioService.gerarResumoFinanceiro(inicio, fim));
    }

    @GetMapping("/pdf")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportarRelatorioPDF(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {

        byte[] pdf = relatorioService.exportarRelatorioPDF(inicio, fim);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "relatorio_financeiro.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @GetMapping("/excel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportarRelatorioExcel(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {

        byte[] excelBytes = relatorioService.exportarRelatorioExcel(inicio, fim);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("relatorio_financeiro.xlsx")
                .build());

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}

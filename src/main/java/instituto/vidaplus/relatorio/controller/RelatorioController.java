package instituto.vidaplus.relatorio.controller;

import instituto.vidaplus.relatorio.dto.RelatorioFinanceiroDTO;
import instituto.vidaplus.relatorio.dto.ResumoFinanceiroDTO;
import instituto.vidaplus.relatorio.service.RelatorioFinanceiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Registrar relatório financeiro",
            description = "Registra um novo relatório financeiro")
    @ApiResponse(responseCode = "200", description = "Relatório registrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RelatorioFinanceiroDTO.class),
                    examples = @ExampleObject(
                            value = "{\"data\": \"2025-10-01\", \"receita\": 1000.0, \"despesa\": 500.0}"
                    )
            )
    )

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RelatorioFinanceiroDTO> registrarRelatorio(@RequestBody RelatorioFinanceiroDTO relatorioDTO) {
        return ResponseEntity.ok(relatorioService.registrarRelatorio(relatorioDTO));
    }

    @Operation(summary = "Buscar relatórios por período",
            description = "Busca relatórios financeiros em um período específico")
    @ApiResponse(responseCode = "200", description = "Relatórios encontrados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RelatorioFinanceiroDTO.class)))
    @GetMapping("/periodo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RelatorioFinanceiroDTO>> buscarRelatorioPorPeriodo(
            @Parameter(description = "Data de início do período", required = true) @RequestParam LocalDate inicio,
            @Parameter(description = "Data de fim do período", required = true) @RequestParam LocalDate fim) {
        return ResponseEntity.ok(relatorioService.buscarRelatorioPorPeriodo(inicio, fim));
    }

    @Operation(summary = "Gerar resumo financeiro",
            description = "Gera um resumo financeiro para um período específico")
    @ApiResponse(responseCode = "200", description = "Resumo gerado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResumoFinanceiroDTO.class)))
    @GetMapping("/resumo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResumoFinanceiroDTO> gerarResumoFinanceiro(
            @Parameter(description = "Data de início do período", required = true) @RequestParam LocalDate inicio,
            @Parameter(description = "Data de fim do período", required = true) @RequestParam LocalDate fim) {
        return ResponseEntity.ok(relatorioService.gerarResumoFinanceiro(inicio, fim));
    }

    @Operation(summary = "Exportar relatório financeiro em PDF",
            description = "Exporta um relatório financeiro em formato PDF para um período específico")
    @ApiResponse(responseCode = "200", description = "Relatório PDF gerado com sucesso",
            content = @Content(mediaType = "application/pdf"))
    @GetMapping("/pdf")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportarRelatorioPDF(
            @Parameter(description = "Data de início do período", required = true) @RequestParam LocalDate inicio,
            @Parameter(description = "Data de fim do período", required = true) @RequestParam LocalDate fim) {

        byte[] pdf = relatorioService.exportarRelatorioPDF(inicio, fim);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "relatorio_financeiro.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @Operation(summary = "Exportar relatório financeiro em Excel",
            description = "Exporta um relatório financeiro em formato Excel para um período específico")
    @ApiResponse(responseCode = "200", description = "Relatório Excel gerado com sucesso",
            content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
    @GetMapping("/excel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportarRelatorioExcel(
            @Parameter(description = "Data de início do período", required = true) @RequestParam LocalDate inicio,
            @Parameter(description = "Data de fim do período", required = true) @RequestParam LocalDate fim) {

        byte[] excelBytes = relatorioService.exportarRelatorioExcel(inicio, fim);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("relatorio_financeiro.xlsx")
                .build());

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}

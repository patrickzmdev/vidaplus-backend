package instituto.vidaplus.relatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioFinanceiroDTO {

    private Long id;
    private LocalDate data;
    private Double receita;
    private Double despesa;
    private Double lucro;
    private Long administradorId;
}

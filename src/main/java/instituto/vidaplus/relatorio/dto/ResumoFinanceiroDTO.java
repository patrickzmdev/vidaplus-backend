package instituto.vidaplus.relatorio.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResumoFinanceiroDTO {
    private Double receitaTotal;
    private Double despesaTotal;
    private Double lucroTotal;
    private LocalDate periodoInicio;
    private LocalDate periodoFim;
}

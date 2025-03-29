package instituto.vidaplus.relatorio.dto;

import instituto.vidaplus.relatorio.model.RelatorioFinanceiro;
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

    public RelatorioFinanceiroDTO(RelatorioFinanceiro relatorioFinanceiro) {
        this.id = relatorioFinanceiro.getId();
        this.data = relatorioFinanceiro.getData();
        this.receita = relatorioFinanceiro.getReceita();
        this.despesa = relatorioFinanceiro.getDespesa();
        this.lucro = relatorioFinanceiro.getLucro();
    }
}

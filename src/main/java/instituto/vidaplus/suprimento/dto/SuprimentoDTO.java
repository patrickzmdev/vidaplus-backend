package instituto.vidaplus.suprimento.dto;

import instituto.vidaplus.suprimento.model.Suprimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuprimentoDTO {

    private Long id;
    private String nome;
    private Integer quantidade;
    private String unidadeMedida;

    public SuprimentoDTO(Suprimento suprimento) {
        this.id = suprimento.getId();
        this.nome = suprimento.getNome();
        this.quantidade = suprimento.getQuantidade();
        this.unidadeMedida = suprimento.getUnidadeMedida();
    }
}

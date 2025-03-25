package instituto.vidaplus.receita.dto;

import instituto.vidaplus.receita.model.ItemReceita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemReceitaDTO {

    private Long id;
    private Long receitaId;
    private String nome;
    private Boolean controlado;
    private Integer quantidade;

    public ItemReceitaDTO(ItemReceita itemReceita) {
        this.id = itemReceita.getId();
        this.receitaId = itemReceita.getReceita().getId();
        this.nome = itemReceita.getNome();
        this.controlado = itemReceita.getControlado();
        this.quantidade = itemReceita.getQuantidade();
    }
}

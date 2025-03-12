package instituto.vidaplus.receita.dto;

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
}

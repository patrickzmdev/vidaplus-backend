package instituto.vidaplus.suprimento.dto;

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
    private Long administradorId;
}

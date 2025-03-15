package instituto.vidaplus.exame.dto;

import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.exame.model.ExameSuprimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExameSuprimentoDTO {

    private Long id;
    private Long exameId;
    private Long suprimentoId;
    private Integer quantidadeUtilizada;

    public ExameSuprimentoDTO(ExameSuprimento exame) {
        this.id = exame.getId();
        this.exameId = exame.getExame().getId();
        this.suprimentoId = exame.getSuprimento().getId();
        this.quantidadeUtilizada = exame.getQuantidadeUtilizada();
    }
}

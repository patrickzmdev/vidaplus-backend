package instituto.vidaplus.internacao.dto;

import instituto.vidaplus.internacao.model.InternacaoSuprimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternacaoSuprimentoDto {

    private Long id;
    private Long internacaoId;
    private Long suprimentoId;
    private Integer quantidadeUtilizada;

    public InternacaoSuprimentoDto(InternacaoSuprimento internacaoSuprimento){
        this.id = internacaoSuprimento.getId();
        this.internacaoId = internacaoSuprimento.getInternacao().getId();
        this.suprimentoId = internacaoSuprimento.getSuprimento().getId();
        this.quantidadeUtilizada = internacaoSuprimento.getQuantidadeUtilizada();
    }
}

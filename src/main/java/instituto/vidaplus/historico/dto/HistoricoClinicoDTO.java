package instituto.vidaplus.historico.dto;

import instituto.vidaplus.historico.enums.TipoHistoricoEnum;
import instituto.vidaplus.historico.model.HistoricoClinico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoClinicoDTO {

    private Long id;
    private Long pacienteId;
    private TipoHistoricoEnum tipoHistorico;
    private String descricao;
    private LocalDateTime dataRegistro;

    public HistoricoClinicoDTO(HistoricoClinico historicoClinico) {
        this.id = historicoClinico.getId();
        this.pacienteId = historicoClinico.getPaciente().getId();
        this.tipoHistorico = historicoClinico.getTipo();
        this.descricao = historicoClinico.getDescricao();
        this.dataRegistro = historicoClinico.getDataRegistro();
    }
}

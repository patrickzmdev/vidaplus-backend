package instituto.vidaplus.historico.dto;

import instituto.vidaplus.historico.enums.TipoHistoricoEnum;
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
}

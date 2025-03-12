package instituto.vidaplus.exame.dto;

import instituto.vidaplus.exame.enums.StatusExameEnum;
import instituto.vidaplus.exame.enums.TipoExameEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExameDTO {

    private Long id;
    private Long pacienteId;
    private Long profissionalId;
    private TipoExameEnum tipoExame;
    private LocalDate dataSolicitacao;
    private StatusExameEnum status;
}

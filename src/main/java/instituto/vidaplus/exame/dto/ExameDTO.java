package instituto.vidaplus.exame.dto;

import instituto.vidaplus.exame.enums.StatusExameEnum;
import instituto.vidaplus.exame.enums.TipoExameEnum;
import instituto.vidaplus.exame.model.Exame;
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
    private LocalDate dataAgendamento;
    private StatusExameEnum status;

    public ExameDTO(Exame exame) {
        this.id = exame.getId();
        this.pacienteId = exame.getPaciente().getId();
        this.profissionalId = exame.getProfissional().getId();
        this.tipoExame = exame.getTipoExame();
        this.dataAgendamento = exame.getDataAgendamento();
        this.status = exame.getStatus();
    }
}

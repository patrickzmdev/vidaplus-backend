package instituto.vidaplus.horario.dto;

import instituto.vidaplus.horario.enums.DiasDaSemanaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDisponivelDTO {

    private Long id;
    private Long agendaId;
    private DiasDaSemanaEnum diaDaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Integer duracaoMediaEmMinutos;
    private Boolean disponivel;
}

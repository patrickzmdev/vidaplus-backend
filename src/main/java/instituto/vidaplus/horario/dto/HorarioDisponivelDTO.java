package instituto.vidaplus.horario.dto;

import instituto.vidaplus.horario.enums.DiasDaSemanaEnum;
import instituto.vidaplus.horario.model.HorarioDisponivel;
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

    public HorarioDisponivelDTO(HorarioDisponivel horarioDisponivel) {
        this.id = horarioDisponivel.getId();
        this.agendaId = horarioDisponivel.getAgenda().getId();
        this.diaDaSemana = horarioDisponivel.getDiaDaSemana();
        this.horaInicio = horarioDisponivel.getHoraInicio();
        this.horaFim = horarioDisponivel.getHoraFim();
        this.duracaoMediaEmMinutos = horarioDisponivel.getDuracaoMediaEmMinutos();
        this.disponivel = horarioDisponivel.getDisponivel();
    }
}

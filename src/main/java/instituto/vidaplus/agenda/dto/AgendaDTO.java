package instituto.vidaplus.agenda.dto;

import instituto.vidaplus.horario.model.HorarioDisponivel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDTO {

    private Long id;
    private Long profissionalId;
    private Boolean ativo;
    private List<HorarioDisponivel> horarioDisponiveis = new ArrayList<>();
    private List<Long> consultas = new ArrayList<>();
}

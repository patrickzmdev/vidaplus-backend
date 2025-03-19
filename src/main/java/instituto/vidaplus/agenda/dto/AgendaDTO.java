package instituto.vidaplus.agenda.dto;

import instituto.vidaplus.agenda.model.Agenda;
import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.horario.model.HorarioDisponivel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDTO {

    private Long id;
    private Long profissionalId;
    private Boolean ativo;
    private List<HorarioDisponivel> horarioDisponiveis = new ArrayList<>();
    private List<Long> consultas = new ArrayList<>();

    public AgendaDTO(Agenda agenda) {
        this.id = agenda.getId();
        this.profissionalId = agenda.getProfissional().getId();
        this.ativo = agenda.getAtivo();
        this.horarioDisponiveis = new ArrayList<>(agenda.getHorariosDisponiveis());
        this.consultas = agenda.getConsultas().stream().map(Consulta::getId).collect(Collectors.toList());
    }
}

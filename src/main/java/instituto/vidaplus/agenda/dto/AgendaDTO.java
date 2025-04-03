package instituto.vidaplus.agenda.dto;

import instituto.vidaplus.agenda.model.Agenda;
import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AgendaDTO {
    private Long id;
    private Boolean ativo;
    private Long profissionalId;
    private String nomeProfissional;
    private List<HorarioDisponivelDTO> horarioDisponiveis;

    public AgendaDTO(Agenda agenda) {
        this.id = agenda.getId();
        this.ativo = agenda.getAtivo();
        this.profissionalId = agenda.getProfissional().getId();
        this.nomeProfissional = agenda.getProfissional().getNome();
        this.horarioDisponiveis = agenda.getHorariosDisponiveis().stream()
                .map(HorarioDisponivelDTO::new)
                .collect(Collectors.toList());
    }
}
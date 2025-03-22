package instituto.vidaplus.consulta.dto;

import instituto.vidaplus.consulta.enums.StatusConsultaEnum;
import instituto.vidaplus.consulta.model.Consulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {

    private Long id;
    private Long pacienteId;
    private Long profissionalId;
    private Long agendaId;
    private Long telemedicinaId;
    private String linkVideoChamada;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private StatusConsultaEnum status;
    private String motivoConsulta;

    public ConsultaDTO(Consulta consulta) {
        this.id = consulta.getId();
        this.pacienteId = consulta.getPaciente().getId();
        this.profissionalId = consulta.getProfissional().getId();
        this.agendaId = consulta.getAgenda().getId();
        this.data = consulta.getData();
        this.horaInicio = consulta.getHoraInicio();
        this.horaFim = consulta.getHoraFim();
        this.status = consulta.getStatus();
        this.motivoConsulta = consulta.getMotivoConsulta();

        if (consulta.getTelemedicina() != null) {
            this.telemedicinaId = consulta.getTelemedicina().getId();
            this.linkVideoChamada = consulta.getTelemedicina().getLinkVideoChamada();
        }
    }
}

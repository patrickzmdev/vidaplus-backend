package instituto.vidaplus.consulta.dto;

import instituto.vidaplus.consulta.enums.StatusConsultaEnum;
import instituto.vidaplus.telemedicina.model.Telemedicina;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {

    private Long id;
    private Long pacienteId;
    private Long profissionalId;
    private Long agendaId;
    private List<Telemedicina> listaTelemedicinas = new ArrayList<>();
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private Boolean isTelemedicina;
    private StatusConsultaEnum status;
    private String motivoConsulta;

}

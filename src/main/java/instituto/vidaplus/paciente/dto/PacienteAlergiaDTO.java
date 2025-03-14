package instituto.vidaplus.paciente.dto;

import instituto.vidaplus.paciente.enums.AlergiaEnum;
import instituto.vidaplus.paciente.model.PacienteAlergia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteAlergiaDTO {
    private Long id;
    private Long pacienteId;
    private AlergiaEnum alergia;
    private String observacao;
    private String descricaoOutraAlergia;

    public PacienteAlergiaDTO(PacienteAlergia pacienteAlergia) {
        this.id = pacienteAlergia.getId();
        this.pacienteId = pacienteAlergia.getPaciente().getId();
        this.alergia = pacienteAlergia.getAlergia();
        this.observacao = pacienteAlergia.getObservacao();
        this.descricaoOutraAlergia = pacienteAlergia.getDescricaoOutraAlergia();
    }
}

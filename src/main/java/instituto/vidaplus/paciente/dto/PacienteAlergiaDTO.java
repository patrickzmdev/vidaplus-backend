package instituto.vidaplus.paciente.dto;

import instituto.vidaplus.paciente.enums.AlergiaEnum;
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
}

package instituto.vidaplus.leito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeitoDTO {

    private Long id;
    private Integer numero;
    private Boolean ocupado;
    private Long pacienteId;
    private Long unidadeHospitalarId;
    private Long administradorId;
}

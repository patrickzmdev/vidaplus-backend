package instituto.vidaplus.leito.dto;

import com.fasterxml.jackson.annotation.*;
import instituto.vidaplus.leito.model.Leito;
import instituto.vidaplus.paciente.dto.PacienteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"pacienteDTO"})
public class LeitoDTO {

    private Long id;
    private Integer numero;
    private Boolean ocupado;
    private Long unidadeHospitalarId;

    public LeitoDTO(Leito leito) {
        this.id = leito.getId();
        this.numero = leito.getNumero();
        this.ocupado = leito.getOcupado();
        this.unidadeHospitalarId = leito.getUnidadeHospitalar().getId();
    }
}

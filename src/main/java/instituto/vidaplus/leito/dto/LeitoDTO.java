package instituto.vidaplus.leito.dto;

import instituto.vidaplus.leito.model.Leito;
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
    private Long unidadeHospitalarId;

    public LeitoDTO(Leito leito) {
        this.id = leito.getId();
        this.numero = leito.getNumero();
        this.ocupado = leito.getOcupado();
        this.unidadeHospitalarId = leito.getUnidadeHospitalar().getId();
    }
}

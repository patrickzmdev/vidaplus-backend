package instituto.vidaplus.unidade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeHospitalarDTO {

    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private Long administradorId;
}

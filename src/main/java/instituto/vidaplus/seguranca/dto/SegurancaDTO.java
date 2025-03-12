package instituto.vidaplus.seguranca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SegurancaDTO {

    private Long id;
    private String tipoCriptografia;
    private String nivelAcesso;
    private String descricao;
    private Boolean conformidadeLGPD;
}

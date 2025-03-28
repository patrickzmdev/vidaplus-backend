package instituto.vidaplus.seguranca.dto;

import lombok.Data;

@Data
public class ValidacaoCodigoRequest {
    private String email;
    private String codigo;
}

package instituto.vidaplus.seguranca.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class RequisicaoRegistro {
    private String username;
    private String email;
    private String senha;

    @Getter
    private boolean admin;
}

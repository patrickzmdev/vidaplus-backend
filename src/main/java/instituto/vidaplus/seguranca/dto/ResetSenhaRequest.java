package instituto.vidaplus.seguranca.dto;

import lombok.Data;

@Data
public class ResetSenhaRequest {
    private String email;
    private String codigo;
    private String novaSenha;
}

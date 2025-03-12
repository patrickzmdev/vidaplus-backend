package instituto.vidaplus.endereco.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}

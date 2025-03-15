package instituto.vidaplus.unidade.dto;

import instituto.vidaplus.unidade.model.UnidadeHospitalar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeHospitalarDTO {

    private Long id;
    private String nome;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String telefone;
    private String email;
    private Long administradorId;

    public UnidadeHospitalarDTO(UnidadeHospitalar unidadeHospitalar) {
        this.id = unidadeHospitalar.getId();
        this.nome = unidadeHospitalar.getNome();
        this.cep = unidadeHospitalar.getCep();
        this.logradouro = unidadeHospitalar.getLogradouro();
        this.bairro = unidadeHospitalar.getBairro();
        this.cidade = unidadeHospitalar.getCidade();
        this.uf = unidadeHospitalar.getUf();
        this.telefone = unidadeHospitalar.getTelefone();
        this.email = unidadeHospitalar.getEmail();
        this.administradorId = unidadeHospitalar.getAdministrador().getId();
    }
}

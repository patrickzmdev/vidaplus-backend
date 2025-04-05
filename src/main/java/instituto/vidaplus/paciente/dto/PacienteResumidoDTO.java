package instituto.vidaplus.paciente.dto;

import instituto.vidaplus.paciente.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResumidoDTO {

    private Long id;
    private String nome;
    private String cep;
    private String telefone;
    private String logradouro;
    private String bairro;
    private String cidade;
    private Boolean possuiPlanoDeSaude;
    private String planoDeSaudeNome;

    public PacienteResumidoDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.cep = paciente.getCep();
        this.logradouro = paciente.getLogradouro();
        this.bairro = paciente.getBairro();
        this.cidade = paciente.getCidade();
        this.possuiPlanoDeSaude = paciente.getPossuiPlanoDeSaude();
        this.planoDeSaudeNome = paciente.getPlanoDeSaudeNome();
    }
}

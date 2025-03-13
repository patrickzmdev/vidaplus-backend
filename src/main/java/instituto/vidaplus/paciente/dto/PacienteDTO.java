package instituto.vidaplus.paciente.dto;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.core.SexoEnum;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.historico.model.HistoricoClinico;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.model.PacienteAlergia;
import instituto.vidaplus.prontuario.model.Prontuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {

    private Long id;
    private String nome;
    private SexoEnum sexo;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private Boolean possuiPlanoDeSaude;
    private String planoDeSaudeNome;
    private Boolean receberNotificacaoEmail;
    private Boolean aceitouTermosTelemedicina;
    private String tokenTeleconsulta;
    private String tipoSanguineo;
    private List<HistoricoClinico> historicoClinico = new ArrayList<>();
    private List<PacienteAlergia> alergias = new ArrayList<>();
    private List<Prontuario> prontuarios = new ArrayList<>();
    private List<Exame> exames = new ArrayList<>();
    private List<Consulta> consultas = new ArrayList<>();
    private Long administradorId;

    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.sexo = paciente.getSexo();
        this.cpf = paciente.getCpf();
        this.dataNascimento = paciente.getDataNascimento();
        this.email = paciente.getEmail();
        this.telefone = paciente.getTelefone();
        this.cep = paciente.getCep();
        this.logradouro = paciente.getLogradouro();
        this.bairro = paciente.getBairro();
        this.cidade = paciente.getCidade();
        this.uf = paciente.getUf();
        this.possuiPlanoDeSaude = paciente.getPossuiPlanoDeSaude();
        this.planoDeSaudeNome = paciente.getPlanoDeSaudeNome();
        this.receberNotificacaoEmail = paciente.getReceberNotificacaoEmail();
        this.aceitouTermosTelemedicina = paciente.getAceitouTermosTelemedicina();
        this.tokenTeleconsulta = paciente.getTokenTeleconsulta();
        this.tipoSanguineo = paciente.getTipoSanguineo();
        this.historicoClinico = paciente.getHistoricoClinico();
        this.alergias = paciente.getAlergias();
        this.prontuarios = paciente.getProntuarios();
        this.exames = paciente.getExames();
        this.consultas = paciente.getConsultas();
        this.administradorId = paciente.getAdministrador().getId();
    }
}

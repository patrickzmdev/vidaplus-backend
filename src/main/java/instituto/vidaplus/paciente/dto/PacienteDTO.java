package instituto.vidaplus.paciente.dto;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.core.SexoEnum;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.historico.model.HistoricoClinico;
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


}

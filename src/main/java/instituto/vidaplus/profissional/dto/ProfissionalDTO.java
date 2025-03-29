package instituto.vidaplus.profissional.dto;

import instituto.vidaplus.core.SexoEnum;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
import instituto.vidaplus.profissional.enums.TipoProfissionalEnum;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.prontuario.model.Prontuario;
import instituto.vidaplus.receita.model.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalDTO {

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
    private String registro;
    private TipoProfissionalEnum tipoProfissional;
    private EspecialidadeEnum especialidade;
    private Boolean permiteTelemedicina;
    private String tokenTelemedicina;
    private List<Prontuario> prontuarios = new ArrayList<>();
    private List<Exame> examesSolicitados = new ArrayList<>();
    private List<Receita> receitasDigitais = new ArrayList<>();

    public ProfissionalDTO(Profissional profissional) {
        this.id = profissional.getId();
        this.nome = profissional.getNome();
        this.sexo = profissional.getSexo();
        this.cpf = profissional.getCpf();
        this.dataNascimento = profissional.getDataNascimento();
        this.email = profissional.getEmail();
        this.telefone = profissional.getTelefone();
        this.cep = profissional.getCep();
        this.logradouro = profissional.getLogradouro();
        this.bairro = profissional.getBairro();
        this.cidade = profissional.getCidade();
        this.uf = profissional.getUf();
        this.registro = profissional.getRegistro();
        this.tipoProfissional = profissional.getTipoProfissional();
        this.especialidade = profissional.getEspecialidade();
        this.permiteTelemedicina = profissional.getPermiteTelemedicina();
        this.tokenTelemedicina = profissional.getTokenTelemedicina();
        this.prontuarios = profissional.getProntuarios();
        this.examesSolicitados = profissional.getExamesSolicitados();
        this.receitasDigitais = profissional.getReceitasDigitais();
    }

}

package instituto.vidaplus.administrador.dto;

import instituto.vidaplus.core.SexoEnum;
import instituto.vidaplus.leito.model.Leito;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.relatorio.model.RelatorioFinanceiro;
import instituto.vidaplus.suprimento.model.Suprimento;
import instituto.vidaplus.unidade.model.UnidadeHospitalar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorDTO {

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
    private List<Long> pacientesIds = new ArrayList<>();
    private List<Long> profissionaisIds = new ArrayList<>();
    private List<Leito> leitos = new ArrayList<>();
    private List<Long> relatorioFinanceirosIds = new ArrayList<>();
    private List<Suprimento> suprimentos = new ArrayList<>();
    private List<UnidadeHospitalar> unidadeHospitalares = new ArrayList<>();
}

package instituto.vidaplus.administrador.model;

import instituto.vidaplus.unidade.model.UnidadeHospitalar;
import instituto.vidaplus.core.Pessoa;
import instituto.vidaplus.leito.model.Leito;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.relatorio.model.RelatorioFinanceiro;
import instituto.vidaplus.suprimento.model.Suprimento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("ADMINISTRADOR")
@Table(name = "administradores")
public class Administrador extends Pessoa {

    @OneToMany(mappedBy = "administrador")
    @Builder.Default
    private List<Paciente> pacientes = new ArrayList<>();

    @OneToMany(mappedBy = "administrador")
    @Builder.Default
    private List<Profissional> profissionais = new ArrayList<>();

    @OneToMany(mappedBy = "administrador")
    @Builder.Default
    private List<Leito> leitos = new ArrayList<>();

    @OneToMany(mappedBy = "administrador")
    @Builder.Default
    private List<RelatorioFinanceiro> relatorioFinanceiros = new ArrayList<>();

    @OneToMany(mappedBy = "administrador")
    @Builder.Default
    private List<Suprimento> suprimentos = new ArrayList<>();

    @OneToMany(mappedBy = "administrador")
    @Builder.Default
    private List<UnidadeHospitalar> unidadesHospitalares = new ArrayList<>();
}

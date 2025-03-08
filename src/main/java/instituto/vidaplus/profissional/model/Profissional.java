package instituto.vidaplus.profissional.model;

import instituto.vidaplus.core.Pessoa;
import instituto.vidaplus.core.SexoEnum;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
import instituto.vidaplus.profissional.enums.TipoProfissionalEnum;
import instituto.vidaplus.prontuario.model.Prontuario;
import jakarta.persistence.*;
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
@DiscriminatorValue("PROFISSIONAL")
@Table(name = "profissionais")
public class Profissional extends Pessoa {

    @Column(nullable = false)
    private String registro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProfissionalEnum tipoProfissional;

    @Enumerated(EnumType.STRING)
    private EspecialidadeEnum especialidade;

    @Column(nullable = false)
    private Boolean permiteTelemedicina;

    @Column(nullable = false)
    private String formacaoAcademica;

    private String tokenTelemedicina;

    @OneToMany(mappedBy = "profissional")
    private List<Prontuario> prontuarios = new ArrayList<>();

    @OneToMany(mappedBy = "profissional")
    private List<Exame> examesSolicitados = new ArrayList<>();
}

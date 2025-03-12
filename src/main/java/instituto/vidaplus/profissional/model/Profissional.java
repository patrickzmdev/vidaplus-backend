package instituto.vidaplus.profissional.model;

import instituto.vidaplus.administrador.model.Administrador;
import instituto.vidaplus.core.Pessoa;
import instituto.vidaplus.core.SexoEnum;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
import instituto.vidaplus.profissional.enums.TipoProfissionalEnum;
import instituto.vidaplus.prontuario.model.Prontuario;
import instituto.vidaplus.receita.model.Receita;
import jakarta.persistence.*;
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

    private String tokenTelemedicina;

    @OneToMany(mappedBy = "profissional")
    @Builder.Default
    private List<Prontuario> prontuarios = new ArrayList<>();

    @OneToMany(mappedBy = "profissional")
    @Builder.Default
    private List<Exame> examesSolicitados = new ArrayList<>();

    @OneToMany(mappedBy = "profissional")
    @Builder.Default
    private List<Receita> receitasDigitais = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;
}

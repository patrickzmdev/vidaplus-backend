package instituto.vidaplus.paciente.model;

import instituto.vidaplus.core.Pessoa;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.historico.model.HistoricoClinico;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("PACIENTE")
@Table(name = "pacientes")
public class Paciente extends Pessoa {

    @Column(nullable = false)
    private Boolean possuiPlanoDeSaude;

    private String planoDeSaudeNome;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoClinico> historicoClinico;

    @Column(nullable = false)
    private Boolean receberNotificacaoEmail = true;

    private Boolean aceitouTermosTelemedicina = false;

    private String tokenTeleconsulta;

    private String tipoSanguineo;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacienteAlergia> alergias = new ArrayList<>();

    @OneToMany(mappedBy = "paciente")
    private List<Prontuario> prontuarios = new ArrayList<>();

    @OneToMany(mappedBy = "paciente")
    private List<Exame> exames = new ArrayList<>();

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas = new ArrayList<>();
}

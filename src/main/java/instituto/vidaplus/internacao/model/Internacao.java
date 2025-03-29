package instituto.vidaplus.internacao.model;

import instituto.vidaplus.leito.model.Leito;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.profissional.model.Profissional;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "internacoes")
public class Internacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "leito_id", nullable = false)
    private Leito leito;

    @Column(nullable = false)
    private LocalDateTime dataAdmissao;

    private LocalDateTime dataAlta;

    @Column(nullable = false)
    private String motivoInternacao;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "medico_responsavel_id")
    private Profissional medicoResponsavel;


    @Column(nullable = false)
    private Boolean ativa = true;
}

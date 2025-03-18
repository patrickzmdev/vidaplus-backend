package instituto.vidaplus.exame.model;

import instituto.vidaplus.administrador.model.Administrador;
import instituto.vidaplus.exame.enums.StatusExameEnum;
import instituto.vidaplus.exame.enums.TipoExameEnum;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.profissional.model.Profissional;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "exames")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoExameEnum tipoExame;

    @Column(nullable = false)
    private LocalDate dataAgendamento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusExameEnum status;

    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;
}

package instituto.vidaplus.consulta.model;

import instituto.vidaplus.agenda.model.Agenda;
import instituto.vidaplus.consulta.enums.StatusConsultaEnum;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.telemedicina.model.Telemedicina;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "agenda_id", nullable = false)
    private Agenda agenda;

    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Telemedicina telemedicina;

    @Column(nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(nullable = false)
    private Boolean isTelemedicina;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsultaEnum status;

    private String motivoConsulta;
}

package instituto.vidaplus.horario.model;

import instituto.vidaplus.agenda.model.Agenda;
import instituto.vidaplus.horario.enums.DiasDaSemanaEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "horarios_disponiveis")
public class HorarioDisponivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agenda_id", nullable = false)
    private Agenda agenda;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiasDaSemanaEnum diasDaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFim;

    @Column(nullable = false)
    private Integer duracaoMediaEmMinutos = 30;

    @Column(nullable = false)
    private Boolean disponivel = true;
}

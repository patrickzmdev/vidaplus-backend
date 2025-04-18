package instituto.vidaplus.consulta.model;

import instituto.vidaplus.agenda.model.Agenda;
import instituto.vidaplus.consulta.enums.StatusConsultaEnum;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.prontuario.model.Prontuario;
import instituto.vidaplus.receita.model.Receita;
import instituto.vidaplus.telemedicina.model.Telemedicina;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    @ToString.Exclude
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    @ToString.Exclude
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "agenda_id", nullable = false)
    @ToString.Exclude
    private Agenda agenda;

    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Telemedicina telemedicina;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receita> receitas = new ArrayList<>();

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prontuario> prontuarios = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsultaEnum status;

    private String motivoConsulta;
}

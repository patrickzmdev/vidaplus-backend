package instituto.vidaplus.agenda.model;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.horario.model.HorarioDisponivel;
import instituto.vidaplus.profissional.model.Profissional;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "agendas")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDisponivel> horariosDisponiveis = new ArrayList<>();

    @OneToMany(mappedBy = "agenda")
    private List<Consulta> consultas = new ArrayList<>();

    @Column(nullable = false)
    private Boolean ativo = true;

}

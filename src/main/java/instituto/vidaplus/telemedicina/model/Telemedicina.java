package instituto.vidaplus.telemedicina.model;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.prontuario.model.Prontuario;
import instituto.vidaplus.receita.model.Receita;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "telemedicina")
public class Telemedicina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    @OneToMany(mappedBy = "telemedicina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receita> receitas = new ArrayList<>();

    @OneToMany(mappedBy = "telemedicina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prontuario> prontuarios = new ArrayList<>();

    @Column(nullable = false)
    private String linkVideoChamada;

    @Column(nullable = false)
    private Boolean videoChamadaSegura = true;
}

package instituto.vidaplus.telemedicina.model;

import instituto.vidaplus.consulta.model.Consulta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String linkVideoChamada;

    @Column(nullable = false)
    private Boolean videoChamadaSegura = true;
}

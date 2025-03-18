package instituto.vidaplus.leito.model;

import instituto.vidaplus.administrador.model.Administrador;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.unidade.model.UnidadeHospitalar;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "leitos")
public class Leito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private Boolean ocupado = false;

    @ManyToOne
    @JoinColumn(name = "unidade_hospitalar_id", nullable = false)
    private UnidadeHospitalar unidadeHospitalar;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;
}

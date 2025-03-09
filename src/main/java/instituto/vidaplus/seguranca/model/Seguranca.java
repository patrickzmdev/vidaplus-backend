package instituto.vidaplus.seguranca.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "seguranca")
public class Seguranca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoCriptografia;

    @Column(nullable = false)
    private String nivelAcesso;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Boolean conformidadeLGPD;
}

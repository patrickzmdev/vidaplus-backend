package instituto.vidaplus.exame.model;

import instituto.vidaplus.suprimento.model.Suprimento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "exames_suprimentos")
public class ExameSuprimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exame_id", nullable = false)
    private Exame exame;

    @ManyToOne
    @JoinColumn(name = "suprimento_id", nullable = false)
    private Suprimento suprimento;

    @Column(nullable = false)
    private Integer quantidadeUtilizada;
}

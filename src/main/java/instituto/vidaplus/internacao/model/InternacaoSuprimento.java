package instituto.vidaplus.internacao.model;

import instituto.vidaplus.suprimento.model.Suprimento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "internacoes_suprimentos")
public class InternacaoSuprimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "internacao_id", nullable = false)
    private Internacao internacao;

    @ManyToOne
    @JoinColumn(name = "suprimento_id", nullable = false)
    private Suprimento suprimento;

    @Column(nullable = false)
    private Integer quantidadeUtilizada;

}

package instituto.vidaplus.relatorio.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "relatorios_financeiros")
public class RelatorioFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private Double receita;

    @Column(nullable = false)
    private Double despesa;

    @Column(nullable = false)
    private Double lucro;
}

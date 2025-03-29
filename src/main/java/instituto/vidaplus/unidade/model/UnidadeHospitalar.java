package instituto.vidaplus.unidade.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "unidades_hospitalares")
public class UnidadeHospitalar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(nullable = false)
    private String cep;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String uf;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;
}

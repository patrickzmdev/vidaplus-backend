package instituto.vidaplus.profissional.dto;

import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
import instituto.vidaplus.profissional.enums.TipoProfissionalEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalResumoDTO {
    private Long id;
    private String nome;
    private String registro;
    private TipoProfissionalEnum tipoProfissional;
    private EspecialidadeEnum especialidade;
    private Boolean permiteTelemedicina;
    private String cidade;
    private String uf;
}

package instituto.vidaplus.receita.dto;

import instituto.vidaplus.receita.model.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDTO {

    private Long id;
    private Long pacienteId;
    private Long profissionalId;
    private Long consultaId;
    private LocalDate dataEmissao;
    private LocalDate dataFimValidade;
    private List<ItemReceitaDTO> medicamentos = new ArrayList<>();

    public ReceitaDTO(Receita receita) {
        this.id = receita.getId();
        this.pacienteId = receita.getPaciente().getId();
        this.profissionalId = receita.getProfissional().getId();
        this.consultaId = receita.getConsulta().getId();
        this.dataEmissao = receita.getDataEmissao();
        this.dataFimValidade = receita.getDataFimValidade();
        receita.getMedicamentos().forEach(itemReceita -> this.medicamentos.add(new ItemReceitaDTO(itemReceita)));
    }
}

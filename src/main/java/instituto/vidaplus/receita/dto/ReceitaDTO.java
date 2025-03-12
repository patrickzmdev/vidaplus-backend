package instituto.vidaplus.receita.dto;

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
    private LocalDate dataEmissao;
    private LocalDate dataFimValidade;
    private List<ItemReceitaDTO> medicamentos = new ArrayList<>();
    private Long telemedicinaId;
}

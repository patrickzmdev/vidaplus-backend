package instituto.vidaplus.prontuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProntuarioDTO {

    private Long id;
    private Long pacienteId;
    private Long profissionalId;
    private LocalDate dataRegistro;
    private String descricao;
    private String diagnostico;
    private String tratamentoIndicado;
    private Long telemedicinaId;
}

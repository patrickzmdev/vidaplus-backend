package instituto.vidaplus.prontuario.dto;

import instituto.vidaplus.prontuario.model.Prontuario;
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
    private Long consultaId;
    private LocalDate dataRegistro;
    private String descricao;
    private String diagnostico;
    private String tratamentoIndicado;

    public ProntuarioDTO(Prontuario prontuario) {
        this.id = prontuario.getId();
        this.pacienteId = prontuario.getPaciente().getId();
        this.profissionalId = prontuario.getProfissional().getId();
        this.consultaId = prontuario.getConsulta().getId();
        this.dataRegistro = prontuario.getDataRegistro();
        this.descricao = prontuario.getDescricao();
        this.diagnostico = prontuario.getDiagnostico();
        this.tratamentoIndicado = prontuario.getTratamentoIndicado();
    }
}

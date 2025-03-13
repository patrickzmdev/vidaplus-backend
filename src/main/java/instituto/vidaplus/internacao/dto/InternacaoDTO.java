package instituto.vidaplus.internacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternacaoDTO {

    private Long id;
    private Long pacienteId;
    private Long leitoId;
    private String dataAdmissao;
    private String dataAlta;
    private String motivoInternacao;
    private String observacoes;
    private Long medicoResponsavelId;
    private Long administradorId;
    private Boolean ativa;
}

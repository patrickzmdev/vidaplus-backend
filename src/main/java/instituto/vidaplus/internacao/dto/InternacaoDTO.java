package instituto.vidaplus.internacao.dto;

import instituto.vidaplus.internacao.model.Internacao;
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
    private String dataEncerramento;
    private String motivoInternacao;
    private String observacoes;
    private Long medicoResponsavelId;
    private Boolean ativa;

    public InternacaoDTO(Internacao internacao) {
        this.id = internacao.getId();
        this.pacienteId = internacao.getPaciente().getId();
        this.leitoId = internacao.getLeito().getId();
        this.dataAdmissao = internacao.getDataAdmissao().toString();
        this.dataEncerramento = internacao.getDataEncerramento() != null ? internacao.getDataEncerramento().toString() : null;
        this.motivoInternacao = internacao.getMotivoInternacao();
        this.observacoes = internacao.getObservacoes();
        this.medicoResponsavelId = internacao.getMedicoResponsavel() != null ? internacao.getMedicoResponsavel().getId() : null;
        this.ativa = internacao.getAtiva();
    }
}

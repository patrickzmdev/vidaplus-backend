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
    private String dataAlta;
    private String motivoInternacao;
    private String observacoes;
    private Long medicoResponsavelId;
    private Long administradorId;
    private Boolean ativa;

    public InternacaoDTO(Internacao internacao) {
        this.id = internacao.getId();
        this.pacienteId = internacao.getPaciente().getId();
        this.leitoId = internacao.getLeito().getId();
        this.dataAdmissao = internacao.getDataAdmissao().toString();
        this.dataAlta = internacao.getDataAlta() != null ? internacao.getDataAlta().toString() : null;
        this.motivoInternacao = internacao.getMotivoInternacao();
        this.observacoes = internacao.getObservacoes();
        this.medicoResponsavelId = internacao.getMedicoResponsavel() != null ? internacao.getMedicoResponsavel().getId() : null;
        this.administradorId = internacao.getAdministrador().getId();
        this.ativa = internacao.getAtiva();
    }
}

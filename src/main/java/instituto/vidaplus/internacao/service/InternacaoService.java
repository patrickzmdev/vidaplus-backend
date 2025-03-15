package instituto.vidaplus.internacao.service;

import instituto.vidaplus.internacao.dto.InternacaoDTO;
import instituto.vidaplus.internacao.dto.InternacaoSuprimentoDto;
import instituto.vidaplus.internacao.model.Internacao;

public interface InternacaoService {
    InternacaoDTO registrarInternacao(Long pacienteId, Long leitoId, Internacao internacao);
    String encerrarInternacao(Long internacaoId);
    InternacaoDTO transferirPaciente(Long internacaoId, Long leitoId);
    InternacaoSuprimentoDto adicionarSuprimentoAUmaInternacao(Long internacaoId, Long suprimentoId, Integer quantidade);
}



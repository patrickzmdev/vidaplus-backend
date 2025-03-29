package instituto.vidaplus.internacao.service;

import instituto.vidaplus.internacao.dto.InternacaoDTO;
import instituto.vidaplus.internacao.dto.InternacaoSuprimentoDto;

public interface InternacaoService {
    InternacaoDTO registrarInternacao(Long pacienteId, Long leitoId, InternacaoDTO internacao);
    String encerrarInternacao(Long internacaoId);
    InternacaoDTO transferirPaciente(Long internacaoId, Long leitoId);
    InternacaoSuprimentoDto adicionarSuprimentoAUmaInternacao(Long internacaoId, Long suprimentoId, Integer quantidade);
}



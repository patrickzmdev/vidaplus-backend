package instituto.vidaplus.internacao.service;

import instituto.vidaplus.internacao.model.Internacao;

public interface InternacaoService {
    public String registrarInternacao(Long pacienteId, Long leitoId, Internacao internacao);
    public String encerrarInternacao(Long internacaoId);
    public String transferirPaciente(Long internacaoId, Long leitoId);
}

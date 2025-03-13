package instituto.vidaplus.internacao.service.impl;

import instituto.vidaplus.internacao.exception.InternacaoNaoEncontradaException;
import instituto.vidaplus.internacao.exception.MotivoInternacaoException;
import instituto.vidaplus.internacao.model.Internacao;
import instituto.vidaplus.internacao.repository.InternacaoRepository;
import instituto.vidaplus.internacao.service.InternacaoService;
import instituto.vidaplus.leito.exception.LeitoNaoExistenteException;
import instituto.vidaplus.leito.exception.LeitoOcupadoException;
import instituto.vidaplus.leito.model.Leito;
import instituto.vidaplus.leito.repository.LeitoRepository;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InternacaoServiceImpl implements InternacaoService {

    private final InternacaoRepository internacaoRepository;
    private final PacienteRepository pacienteRepository;
    private final LeitoRepository leitoRepository;

    @Override
    public String registrarInternacao(Long pacienteId, Long leitoId, Internacao internacao) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        Leito leito = leitoRepository.findById(leitoId)
                .orElseThrow(() -> new LeitoNaoExistenteException("Leito não encontrado"));

        if (leito.getOcupado()) {
            throw new LeitoOcupadoException("Leito já está ocupado");
        }

        if (internacao.getMotivoInternacao().isEmpty()) {
            throw new MotivoInternacaoException("Motivo da internação é obrigatório");
        }

        Internacao efetivarInternacao = new Internacao();
        efetivarInternacao.setPaciente(paciente);
        efetivarInternacao.setLeito(leito);
        efetivarInternacao.setDataAdmissao(LocalDateTime.now());
        efetivarInternacao.setMotivoInternacao(internacao.getMotivoInternacao());
        efetivarInternacao.setObservacoes(internacao.getObservacoes());
        efetivarInternacao.setMedicoResponsavel(internacao.getMedicoResponsavel());
        efetivarInternacao.setAdministrador(internacao.getAdministrador());
        efetivarInternacao.setAtiva(true);

        internacaoRepository.save(efetivarInternacao);
        return "Internação efetivada com sucesso";
    }

    @Override
    public String encerrarInternacao(Long internacaoId) {
        Internacao internacao = internacaoRepository.findById(internacaoId)
                .orElseThrow(() -> new InternacaoNaoEncontradaException("Internação não encontrada"));

        internacao.setDataAlta(LocalDateTime.now());
        internacao.setAtiva(false);

        internacaoRepository.save(internacao);
        return "Internação encerrada com sucesso";
    }

    @Override
    public String transferirPaciente(Long internacaoId, Long leitoId) {
        Internacao internacao = internacaoRepository.findById(internacaoId)
                .orElseThrow(() -> new InternacaoNaoEncontradaException ("Internação não encontrada"));

        Leito novoLeito = leitoRepository.findById(leitoId)
                .orElseThrow(() -> new LeitoNaoExistenteException("Leito não encontrado"));

        if (novoLeito.getOcupado()) {
            throw new LeitoOcupadoException("Leito já está ocupado");
        }

        Leito leitoAntigo = internacao.getLeito();
        leitoAntigo.setOcupado(false);

        internacao.setLeito(novoLeito);
        novoLeito.setOcupado(true);

        leitoRepository.save(leitoAntigo);
        leitoRepository.save(novoLeito);
        internacaoRepository.save(internacao);
        return "Paciente transferido com sucesso";
    }
}

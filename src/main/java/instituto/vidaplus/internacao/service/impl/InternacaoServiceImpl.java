package instituto.vidaplus.internacao.service.impl;

import instituto.vidaplus.internacao.dto.InternacaoDTO;
import instituto.vidaplus.internacao.dto.InternacaoSuprimentoDto;
import instituto.vidaplus.internacao.exception.InternacaoNaoEncontradaException;
import instituto.vidaplus.internacao.exception.MotivoInternacaoException;
import instituto.vidaplus.internacao.model.Internacao;
import instituto.vidaplus.internacao.model.InternacaoSuprimento;
import instituto.vidaplus.internacao.repository.InternacaoRepository;
import instituto.vidaplus.internacao.repository.InternacaoSuprimentoRepository;
import instituto.vidaplus.internacao.service.InternacaoService;
import instituto.vidaplus.leito.exception.LeitoNaoExistenteException;
import instituto.vidaplus.leito.exception.LeitoOcupadoException;
import instituto.vidaplus.leito.model.Leito;
import instituto.vidaplus.leito.repository.LeitoRepository;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.profissional.repository.ProfissionalRepository;
import instituto.vidaplus.suprimento.exception.QuantidadeSuprimentoException;
import instituto.vidaplus.suprimento.exception.SuprimentoNaoEncontradoException;
import instituto.vidaplus.suprimento.model.Suprimento;
import instituto.vidaplus.suprimento.repository.SuprimentoRepository;
import instituto.vidaplus.utils.validador.ValidadorMedico;
import instituto.vidaplus.utils.validador.ValidadorPacienteNaoInternado;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InternacaoServiceImpl implements InternacaoService {

    private final InternacaoRepository internacaoRepository;
    private final PacienteRepository pacienteRepository;
    private final LeitoRepository leitoRepository;
    private final SuprimentoRepository suprimentoRepository;
    private final InternacaoSuprimentoRepository internacaoSuprimentoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ValidadorMedico validadorMedico;
    private final ValidadorPacienteNaoInternado validadorPacienteNaoInternado;
    private static final Logger logger = LoggerFactory.getLogger(InternacaoServiceImpl.class);

    @Override
    @Transactional
    public InternacaoDTO registrarInternacao(Long pacienteId, Long leitoId, InternacaoDTO internacao) {
        logger.info("Iniciando o registro de internação para o paciente com ID: {}", pacienteId);
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        Leito leito = leitoRepository.findById(leitoId)
                .orElseThrow(() -> new LeitoNaoExistenteException("Leito não encontrado"));

        Profissional profissional = profissionalRepository.findById(internacao.getMedicoResponsavelId())
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));

        validadorMedico.validarMedico(internacao.getMedicoResponsavelId());
        validadorPacienteNaoInternado.validarPacienteNaoInternado(pacienteId);

        if (leito.getOcupado()) {
            throw new LeitoOcupadoException("Leito já está ocupado");
        }

        if (internacao.getMotivoInternacao().isEmpty()) {
            throw new MotivoInternacaoException("Motivo da internação é obrigatório");
        }

        leito.setOcupado(true);

        Internacao efetivarInternacao = new Internacao();
        efetivarInternacao.setPaciente(paciente);
        efetivarInternacao.setLeito(leito);
        efetivarInternacao.setDataAdmissao(LocalDateTime.now());
        efetivarInternacao.setMotivoInternacao(internacao.getMotivoInternacao());
        efetivarInternacao.setObservacoes(internacao.getObservacoes());
        efetivarInternacao.setMedicoResponsavel(profissional);
        efetivarInternacao.setAtiva(true);

        Internacao internacaoSalva = internacaoRepository.save(efetivarInternacao);
        logger.debug("Internacao: {}", internacaoSalva);
        return new InternacaoDTO(internacaoSalva);
    }

    @Override
    public String encerrarInternacao(Long internacaoId) {
        logger.info("Encerrando internação com ID: {}", internacaoId);
        Internacao internacao = internacaoRepository.findById(internacaoId)
                .orElseThrow(() -> new InternacaoNaoEncontradaException("Internação não encontrada"));

        internacao.setDataEncerramento(LocalDateTime.now());
        internacao.setAtiva(false);

        internacaoRepository.save(internacao);
        logger.debug("Internacao: {}", internacao);
        return "Internação encerrada com sucesso";
    }

    @Override
    @Transactional
    public InternacaoDTO transferirPaciente(Long internacaoId, Long leitoId) {
        logger.info("Iniciando paciente com ID: {}", internacaoId);
        Internacao internacao = internacaoRepository.findById(internacaoId)
                .orElseThrow(() -> new InternacaoNaoEncontradaException ("Internação não encontrada"));

        Leito novoLeito = leitoRepository.findById(leitoId)
                .orElseThrow(() -> new LeitoNaoExistenteException("Leito não encontrado"));

        if (novoLeito.getOcupado()) {
            throw new LeitoOcupadoException("Leito já está ocupado");
        }

        if (!internacao.getAtiva()) {
            throw new MotivoInternacaoException("Internação não está ativa");
        }

        Leito leitoAntigo = internacao.getLeito();
        leitoAntigo.setOcupado(false);

        internacao.setLeito(novoLeito);
        novoLeito.setOcupado(true);

        leitoRepository.save(leitoAntigo);
        leitoRepository.save(novoLeito);
        Internacao internacaoTransferida = internacaoRepository.save(internacao);
        logger.debug("Internacao: {}", internacaoTransferida);
        return new InternacaoDTO(internacaoTransferida);
    }

    @Override
    @Transactional
    public InternacaoSuprimentoDto adicionarSuprimentoAUmaInternacao(Long internacaoId, Long suprimentoId, Integer quantidadeUtilizada) {
        logger.info("Iniciando suprimento a internacao com ID: {}", internacaoId);
        Internacao internacao = internacaoRepository.findById(internacaoId)
                .orElseThrow(() -> new InternacaoNaoEncontradaException("Internação não encontrada"));

        Suprimento suprimento = suprimentoRepository.findById(suprimentoId)
                .orElseThrow(() -> new SuprimentoNaoEncontradoException("Suprimento não encontrado"));

        if(suprimento.getQuantidade() < quantidadeUtilizada){
            throw new QuantidadeSuprimentoException("Quantidade de suprimento insuficiente");
        }

        suprimento.setQuantidade(suprimento.getQuantidade() - quantidadeUtilizada);
        suprimentoRepository.save(suprimento);

        InternacaoSuprimento internacaoSuprimento = new InternacaoSuprimento();
        internacaoSuprimento.setInternacao(internacao);
        internacaoSuprimento.setSuprimento(suprimento);
        internacaoSuprimento.setQuantidadeUtilizada(quantidadeUtilizada);
        InternacaoSuprimento internacaoSuprimentoSalvo = internacaoSuprimentoRepository.save(internacaoSuprimento);
        logger.debug("InternacaoSuprimento: {}", internacaoSuprimento);
        return new InternacaoSuprimentoDto(internacaoSuprimentoSalvo);
    }
}

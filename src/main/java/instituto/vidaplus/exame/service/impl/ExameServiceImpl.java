package instituto.vidaplus.exame.service.impl;

import instituto.vidaplus.email.service.EmailService;
import instituto.vidaplus.exame.dto.ExameDTO;
import instituto.vidaplus.exame.dto.ExameSuprimentoDTO;
import instituto.vidaplus.exame.enums.StatusExameEnum;
import instituto.vidaplus.exame.exception.ExameNaoEncontradoException;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.exame.model.ExameSuprimento;
import instituto.vidaplus.exame.repository.ExameRepository;
import instituto.vidaplus.exame.repository.ExameSuprimentoRepository;
import instituto.vidaplus.exame.service.ExameService;
import instituto.vidaplus.exception.genericas.DataInvalidaException;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.repository.ProfissionalRepository;
import instituto.vidaplus.suprimento.exception.QuantidadeSuprimentoException;
import instituto.vidaplus.suprimento.exception.SuprimentoNaoEncontradoException;
import instituto.vidaplus.suprimento.model.Suprimento;
import instituto.vidaplus.suprimento.repository.SuprimentoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

@Service
@RequiredArgsConstructor
public class ExameServiceImpl implements ExameService {

    private final ExameRepository exameRepository;
    private final SuprimentoRepository suprimentoRepository;
    private final ExameSuprimentoRepository exameSuprimentoRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(ExameServiceImpl.class);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ExameDTO agendarExame(Long pacienteId, ExameDTO exameDTO) {

        logger.info("Agendar exame: {}", exameDTO);
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        if(exameDTO.getDataAgendamento().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            throw new DataInvalidaException("Data de agendamento inválida");
        }

        Exame exame = new Exame();
        exame.setPaciente(paciente);
        exame.setProfissional(profissionalRepository.findById(exameDTO.getProfissionalId())
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado")));
        exame.setTipoExame(exameDTO.getTipoExame());
        exame.setDataAgendamento(exameDTO.getDataAgendamento());
        exame.setStatus(StatusExameEnum.AGENDADO);
        emailService.confirmacaoExame(paciente, "Confirmação de Exame", exame);
        exameRepository.save(exame);
        logger.debug("Exame: {}", exame);
        return new ExameDTO(exame);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ExameDTO buscarExame(Long id) {
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new ExameNaoEncontradoException("Exame não encontrado"));
        return new ExameDTO(exame);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ExameDTO> buscarExamesPorPaciente(Long pacienteId, Pageable pageable) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
        return exameRepository.findByPacienteId(paciente.getId(), pageable)
                .map(ExameDTO::new);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public String finalizarExame(Long id) {
        logger.info("Finalizar exame: {}", id);
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new ExameNaoEncontradoException("Exame não encontrado"));
        exame.setStatus(StatusExameEnum.REALIZADO);
        exameRepository.save(exame);
        logger.debug("Exame: {}", exame);
        return "Exame finalizado com sucesso";
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String cancelarExame(Long id) {
        logger.info("Cancelar exame: {}", id);
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new ExameNaoEncontradoException("Exame não encontrado"));
        exame.setStatus(StatusExameEnum.CANCELADO);
        exameRepository.save(exame);
        logger.debug("Exame: {}", exame);
        return "Exame cancelado com sucesso";
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ExameSuprimentoDTO adicionarSuprimentoAExame(Long exameId, Long suprimentoId, Integer quantidadeUtilizada) {
        logger.info("Adicionar suprimento ao exame: {} e suprimento: {}", exameId, suprimentoId);
        Exame exame = exameRepository.findById(exameId)
                .orElseThrow(() -> new ExameNaoEncontradoException("Exame não encontrado"));
        Suprimento suprimento = suprimentoRepository.findById(suprimentoId)
                .orElseThrow(() -> new SuprimentoNaoEncontradoException("Suprimento não encontrado"));

        if (suprimento.getQuantidade() < quantidadeUtilizada) {
           throw new QuantidadeSuprimentoException("Estoque insuficiente para o suprimento: " + suprimento.getNome());
        }

        if(exame.getStatus().equals(StatusExameEnum.CANCELADO)) {
            throw new ExameNaoEncontradoException("Exame cancelado");
        }

        suprimento.setQuantidade(suprimento.getQuantidade() - quantidadeUtilizada);
        suprimentoRepository.save(suprimento);

        ExameSuprimento exameSuprimento = new ExameSuprimento();
        exameSuprimento.setExame(exame);
        exameSuprimento.setSuprimento(suprimento);
        exameSuprimento.setQuantidadeUtilizada(quantidadeUtilizada);
        exameSuprimentoRepository.save(exameSuprimento);
        logger.debug("Suprimento: {}", exameSuprimento);
        return new ExameSuprimentoDTO(exameSuprimento);
    }
}

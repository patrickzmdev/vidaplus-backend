package instituto.vidaplus.agenda.service.impl;

import instituto.vidaplus.agenda.dto.AgendaDTO;
import instituto.vidaplus.agenda.exception.AgendaNaoEncontradaException;
import instituto.vidaplus.agenda.model.Agenda;
import instituto.vidaplus.agenda.repository.AgendaRepository;
import instituto.vidaplus.agenda.service.AgendaService;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.profissional.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;
    private final ProfissionalRepository profissionalRepository;
    private static final Logger logger = LoggerFactory.getLogger(AgendaServiceImpl.class);

    @Override
    @Transactional
    public AgendaDTO criarAgenda(Long profissionalId, AgendaDTO agendaDTO) {
        logger.info("Criando uma agenda para o profissional: {}", profissionalId);
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));

        Agenda agenda = new Agenda();
        agenda.setProfissional(profissional);
        agenda.setAtivo(true);
        Agenda agendaSalva = agendaRepository.save(agenda);

        logger.debug("Agenda salva: {}", agendaSalva);
        return new AgendaDTO(agendaSalva);
    }

    @Override
    public AgendaDTO buscarAgendaPorId(Long id) {
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));
        return new AgendaDTO(agenda);
    }

    @Override
    public Page<AgendaDTO> listarAgendas(Pageable pageable) {
        return agendaRepository.findAll(pageable).map(AgendaDTO::new);
    }

    @Override
    public AgendaDTO atualizarAgenda(Long id, AgendaDTO agendaDTO) {
        logger.info("Atualizando a agenda com ID: {}", id);
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));

        if(agendaDTO.getProfissionalId() != null) {
            Profissional profissional = profissionalRepository.findById(agendaDTO.getProfissionalId())
                    .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));
            agenda.setProfissional(profissional);
        }

        if(agendaDTO.getAtivo() != null) {
            agenda.setAtivo(agendaDTO.getAtivo());
        }

        logger.debug("Agenda atualizada: {}", agenda);
        return new AgendaDTO(agendaRepository.save(agenda));
    }

    @Override
    @Transactional
    public String deletarAgenda(Long id) {
        logger.info("Deletando a agenda com ID: {}", id);
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));

        agendaRepository.delete(agenda);
        logger.debug("Agenda deletada: {}", agenda);
        return "Agenda deletada com sucesso";
    }

    @Override
    public AgendaDTO buscarAgendaPorProfissional(Long profissionalId) {
        Agenda agenda = agendaRepository.findByProfissionalId(profissionalId)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));
        return new AgendaDTO(agenda);
    }

    @Override
    @Transactional
    public AgendaDTO ativarAgenda(Long id) {
        logger.info("Ativarando a agenda com ID: {}", id);
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));
        agenda.setAtivo(true);
        logger.debug("Agenda ativada: {}", agenda);
        return new AgendaDTO(agendaRepository.save(agenda));
    }

    @Override
    @Transactional
    public AgendaDTO desativarAgenda(Long id) {
        logger.info("Desativarando a agenda com ID: {}", id);
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));
        agenda.setAtivo(false);
        logger.debug("Agenda desatiada: {}", agenda);
        return new AgendaDTO(agendaRepository.save(agenda));
    }
}

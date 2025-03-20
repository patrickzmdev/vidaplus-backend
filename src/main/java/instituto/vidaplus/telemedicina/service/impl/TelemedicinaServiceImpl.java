package instituto.vidaplus.telemedicina.service.impl;

import instituto.vidaplus.consulta.enums.StatusConsultaEnum;
import instituto.vidaplus.consulta.exception.ConsultaNaoEncontradaException;
import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.consulta.repository.ConsultaRepository;
import instituto.vidaplus.telemedicina.dto.TelemedicinaDTO;
import instituto.vidaplus.telemedicina.exception.TelemedicinaJaCadastradaException;
import instituto.vidaplus.telemedicina.exception.TelemedicinaNaoEncontradaException;
import instituto.vidaplus.telemedicina.model.Telemedicina;
import instituto.vidaplus.telemedicina.repository.TelemedicinaRepository;
import instituto.vidaplus.telemedicina.service.TelemedicinaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TelemedicinaServiceImpl implements TelemedicinaService {

    private final TelemedicinaRepository telemedicinaRepository;
    private final ConsultaRepository consultaRepository;

    @Override
    @Transactional
    public TelemedicinaDTO criarTelemedicina(Long consultaId, String linkVideoChamada) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));

        if(telemedicinaRepository.existsByConsultaId(consultaId)) {
            throw new TelemedicinaJaCadastradaException("Telemedicina já cadastrada para essa consulta");
        }

        Telemedicina telemedicina = new Telemedicina();
        telemedicina.setConsulta(consulta);
        telemedicina.setLinkVideoChamada(linkVideoChamada != null ? linkVideoChamada : gerarLinkTelemedicina());
        telemedicina.setVideoChamadaSegura(true);
        Telemedicina telemedicinaSalva = telemedicinaRepository.save(telemedicina);

        return new TelemedicinaDTO(telemedicinaSalva);
    }

    @Override
    public TelemedicinaDTO buscarTelemedicinaPorConsultaId(Long consultaId) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
        Telemedicina telemedicina = telemedicinaRepository.findByConsultaId(consultaId)
                .orElseThrow(() -> new TelemedicinaNaoEncontradaException("Telemedicina não encontrada"));

        return new TelemedicinaDTO(telemedicina);
    }

    @Override
    public TelemedicinaDTO atualizarLinkTelemedicina(Long telemedicinaId) {
        Telemedicina telemedicina = telemedicinaRepository.findById(telemedicinaId)
                .orElseThrow(() -> new TelemedicinaNaoEncontradaException("Telemedicina não encontrada"));

        telemedicina.setLinkVideoChamada(gerarLinkTelemedicina());
        Telemedicina telemedicinaSalva = telemedicinaRepository.save(telemedicina);
        return  new TelemedicinaDTO(telemedicinaSalva);
    }

    @Override
    public String deletarTelemedicina(Long telemedicinaId) {
        Telemedicina telemedicina = telemedicinaRepository.findById(telemedicinaId)
                .orElseThrow(() -> new TelemedicinaNaoEncontradaException("Telemedicina não encontrada"));
        telemedicinaRepository.delete(telemedicina);
        return "Telemedicina removida com sucesso";
    }

    @Override
    public Boolean consultaEstaEmTelemedicina(Long consultaId) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
        return telemedicinaRepository.existsByConsultaId(consultaId);
    }

    @Override
    @Transactional
    public TelemedicinaDTO iniciarTelemedicina(Long telemedicinaId) {
        Telemedicina telemedicina = telemedicinaRepository.findById(telemedicinaId)
                .orElseThrow(() -> new TelemedicinaNaoEncontradaException("Telemedicina não encontrada"));
        Consulta consulta = telemedicina.getConsulta();
        consulta.setStatus(StatusConsultaEnum.EM_ANDAMENTO);
        consultaRepository.save(consulta);

        return new TelemedicinaDTO(telemedicina);
    }

    @Override
    @Transactional
    public TelemedicinaDTO finalizarTelemedicina(Long telemedicinaId) {
        Telemedicina telemedicina = telemedicinaRepository.findById(telemedicinaId)
                .orElseThrow(() -> new TelemedicinaNaoEncontradaException("Telemedicina não encontrada"));
        Consulta consulta = telemedicina.getConsulta();
        consulta.setStatus(StatusConsultaEnum.CONCLUIDA);
        consultaRepository.save(consulta);

        return new TelemedicinaDTO(telemedicina);
    }

    private String gerarLinkTelemedicina() {
        String linkSeguro = UUID.randomUUID().toString();
        return "https://vidaplus.com.br/telemedicina/" + linkSeguro;
    }
}

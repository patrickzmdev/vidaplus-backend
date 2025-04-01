package instituto.vidaplus.historico.service.impl;

import instituto.vidaplus.historico.dto.HistoricoClinicoDTO;
import instituto.vidaplus.historico.enums.TipoHistoricoEnum;
import instituto.vidaplus.historico.model.HistoricoClinico;
import instituto.vidaplus.historico.repository.HistoricoClinicoRepository;
import instituto.vidaplus.historico.service.HistoricoClinicoService;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HistoricoClinicoServiceImpl implements HistoricoClinicoService {

    private final HistoricoClinicoRepository historicoClinicoRepository;
    private final PacienteRepository pacienteRepository;
    private static final Logger logger = LoggerFactory.getLogger(HistoricoClinicoServiceImpl.class);

    @Override
    public HistoricoClinicoDTO registrarHistorico(Long pacienteId, HistoricoClinicoDTO historicoClinicoDTO) {
        logger.info("Registrando Historico: {}", historicoClinicoDTO);
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        HistoricoClinico historico = new HistoricoClinico();
        historico.setPaciente(paciente);
        historico.setTipo(historicoClinicoDTO.getTipoHistorico());
        historico.setDescricao(historicoClinicoDTO.getDescricao());
        historico.setDataRegistro(LocalDateTime.now());
        HistoricoClinico historicoSalvo = historicoClinicoRepository.save(historico);
        logger.debug("Historico: {}", historicoSalvo);
        return new HistoricoClinicoDTO(historicoSalvo);
    }

    @Override
    public Page<HistoricoClinicoDTO> buscarHistoricoPorPaciente(Long pacienteId, Pageable pageable) {
        return historicoClinicoRepository.findByPacienteIdOrderByDataRegistroDesc(pacienteId, pageable)
                .map(HistoricoClinicoDTO::new);
    }

    @Override
    public Page<HistoricoClinicoDTO> buscarHistoricoPorPacienteETipo(Long pacienteId, TipoHistoricoEnum tipoHistoricoEnum, Pageable pageable) {
        return historicoClinicoRepository.findByPacienteIdAndTipoOrderByDataRegistroDesc(pacienteId, tipoHistoricoEnum, pageable)
                .map(HistoricoClinicoDTO::new);
    }
}

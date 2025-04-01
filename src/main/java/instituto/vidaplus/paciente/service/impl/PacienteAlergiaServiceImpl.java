package instituto.vidaplus.paciente.service.impl;

import instituto.vidaplus.paciente.dto.PacienteAlergiaDTO;
import instituto.vidaplus.paciente.enums.AlergiaEnum;
import instituto.vidaplus.paciente.exception.AlergiaJaCadastradaException;
import instituto.vidaplus.paciente.exception.AlergiaNaoEncontradaException;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.model.PacienteAlergia;
import instituto.vidaplus.paciente.repository.PacienteAlergiaRepository;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.paciente.service.PacienteAlergiaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteAlergiaServiceImpl implements PacienteAlergiaService {

    private final PacienteRepository pacienteRepository;
    private final PacienteAlergiaRepository pacienteAlergiaRepository;
    private static final Logger logger = LoggerFactory.getLogger(PacienteAlergiaServiceImpl.class);

    @Transactional
    public PacienteAlergiaDTO adicionarAlergia(Long pacienteId, AlergiaEnum alergia, String observacao) {
        logger.info("Adicionando alergia: {}", alergia);
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        PacienteAlergia pacienteAlergiaJaCadastrada = pacienteAlergiaRepository.findByPacienteIdAndAlergia(pacienteId, alergia);

        if(pacienteAlergiaJaCadastrada != null) {
            throw new AlergiaJaCadastradaException("Alergia já cadastrada para o paciente");
        }

        PacienteAlergia pacienteAlergia = new PacienteAlergia();
        pacienteAlergia.setPaciente(paciente);
        pacienteAlergia.setAlergia(alergia);
        pacienteAlergia.setObservacao(observacao);

        PacienteAlergia pacienteSalvo = pacienteAlergiaRepository.save(pacienteAlergia);
        logger.debug("Paciente: {}", pacienteAlergia);
        return new PacienteAlergiaDTO(pacienteSalvo);
    }

    public List<PacienteAlergiaDTO> listaAlergiasPaciente(Long pacienteId, PacienteAlergiaDTO pacienteAlergia) {
        if(!pacienteRepository.existsById(pacienteId)) {
            throw new PacienteNaoEncontradoException("Paciente não encontrado");
        }

        List<PacienteAlergia> alergias = pacienteAlergiaRepository.findByPacienteId(pacienteId);

        return alergias.stream().map(PacienteAlergiaDTO::new).collect(Collectors.toList());
    }

    @Override
    public String excluirAlergia(Long pacienteId, AlergiaEnum alergia) {
        logger.info("Excluindo alergia: {}", alergia);
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        PacienteAlergia pacienteAlergia = pacienteAlergiaRepository.findByPacienteIdAndAlergia(pacienteId, alergia);

        if(pacienteAlergia == null) {
            throw new AlergiaNaoEncontradaException("Alergia não encontrada para o paciente");
        }

        pacienteAlergiaRepository.delete(pacienteAlergia);
        logger.debug("Paciente: {}", pacienteAlergia);
        return "Alergia excluída com sucesso";
    }
}

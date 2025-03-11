package instituto.vidaplus.paciente.service.impl;

import instituto.vidaplus.paciente.enums.AlergiaEnum;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.model.PacienteAlergia;
import instituto.vidaplus.paciente.repository.PacienteAlergiaRepository;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.paciente.service.PacienteAlergiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PacienteAlergiaServiceImpl implements PacienteAlergiaService {

    private final PacienteRepository pacienteRepository;
    private final PacienteAlergiaRepository pacienteAlergiaRepository;

    @Transactional
    public void adicionarAlergia(Long pacienteId, AlergiaEnum alergia, String observacao) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        PacienteAlergia pacienteAlergia = new PacienteAlergia();
        pacienteAlergia.setPaciente(paciente);
        pacienteAlergia.setAlergia(alergia);
        pacienteAlergia.setObservacao(observacao);

        pacienteAlergiaRepository.save(pacienteAlergia);
    }

    @Transactional
    public void removerAlergia(Long alergiaId) {
        pacienteAlergiaRepository.deleteById(alergiaId);
    }

    public boolean pacientePossuiAlergias(Long pacienteId) {
        return pacienteAlergiaRepository.existsByPacienteId(pacienteId);
    }
}

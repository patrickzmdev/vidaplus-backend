package instituto.vidaplus.utils.validador.impl;

import instituto.vidaplus.internacao.repository.InternacaoRepository;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.utils.validador.ValidadorPacienteNaoInternado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorPacienteNaoInternadoImpl implements ValidadorPacienteNaoInternado {

    private final InternacaoRepository internacaoRepository;

    @Override
    public void validarPacienteNaoInternado(Long pacienteId) {
        boolean pacienteInternado = internacaoRepository.existsByPacienteIdAndAtivaTrue(pacienteId);

        if (pacienteInternado) {
            throw new PacienteNaoEncontradoException("Paciente já está internado");
        }
    }
}

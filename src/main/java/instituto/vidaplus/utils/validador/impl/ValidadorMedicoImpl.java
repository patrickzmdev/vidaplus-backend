package instituto.vidaplus.utils.validador.impl;

import instituto.vidaplus.profissional.enums.TipoProfissionalEnum;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEMedicoException;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.profissional.repository.ProfissionalRepository;
import instituto.vidaplus.utils.validador.ValidadorMedico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorMedicoImpl implements ValidadorMedico {

    private final ProfissionalRepository profissionalRepository;

    @Override
    public void validarMedico(Long medicoId) {
        Profissional profissional = profissionalRepository.findById(medicoId)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));

        if(!TipoProfissionalEnum.MEDICO.equals(profissional.getTipoProfissional())){
            throw new ProfissionalNaoEMedicoException("Profissional não é um médico");
        }

    }
}

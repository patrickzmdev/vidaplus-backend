package instituto.vidaplus.utils.validador.impl;

import instituto.vidaplus.exception.genericas.CepInvalidoException;
import instituto.vidaplus.exception.genericas.TelefoneInvalidoException;
import instituto.vidaplus.utils.validador.ValidadorCep;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidadorCepImpl implements ValidadorCep {
    @Override
    public void validarCep(String cep) {
        String regex = "^\\d{8}$";
        if (!Pattern.matches(regex, cep)) {
            throw new CepInvalidoException("Cep inválido.");
        }
    }
}

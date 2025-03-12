package instituto.vidaplus.utils.validador.impl;

import instituto.vidaplus.exception.genericas.TelefoneInvalidoException;
import instituto.vidaplus.utils.validador.ValidadorTelefone;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidadorTelefoneImpl implements ValidadorTelefone {
    @Override
    public void validarTelefone(String telefone) {
        String regex = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$";
        if (!Pattern.matches(regex, telefone)) {
            throw new TelefoneInvalidoException("Telefone inválido.");
        }
    }
}

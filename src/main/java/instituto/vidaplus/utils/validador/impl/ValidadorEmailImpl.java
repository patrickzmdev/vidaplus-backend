package instituto.vidaplus.utils.validador.impl;

import instituto.vidaplus.exception.genericas.EmailInvalidoException;
import instituto.vidaplus.utils.validador.ValidadorEmail;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidadorEmailImpl implements ValidadorEmail {
    @Override
    public void validarEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(regex, email)) {
            throw new EmailInvalidoException("Email inválido.");
        }
    }
}

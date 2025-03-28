package instituto.vidaplus.utils.validador.impl;

import instituto.vidaplus.exception.genericas.SenhaInvalidaException;
import instituto.vidaplus.utils.validador.ValidadorSenha;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidadorSenhaImpl implements ValidadorSenha {

        private static final int TAMANHO_MINIMO = 8;
        private static final String REGEX_MAIUSCULA = ".*[A-Z].*";
        private static final String REGEX_MINUSCULA = ".*[a-z].*";
        private static final String REGEX_NUMERO = ".*[0-9].*";
        private static final String REGEX_ESPECIAL = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*";

        public void validarSenha(String senha){
            List<String> erros = new ArrayList<>();

            if (senha.length() < TAMANHO_MINIMO) {
                erros.add("A senha deve ter no mínimo " + TAMANHO_MINIMO + " caracteres");
            }

            if (!senha.matches(REGEX_MAIUSCULA)) {
                erros.add("A senha deve conter pelo menos uma letra maiúscula");
            }

            if (!senha.matches(REGEX_MINUSCULA)) {
                erros.add("A senha deve conter pelo menos uma letra minúscula");
            }

            if (!senha.matches(REGEX_NUMERO)) {
                erros.add("A senha deve conter pelo menos um número");
            }

            if (!senha.matches(REGEX_ESPECIAL)) {
                erros.add("A senha deve conter pelo menos um caractere especial");
            }

            if (!erros.isEmpty()) {
                throw new SenhaInvalidaException(String.join(". ", erros));
            }
        }
}

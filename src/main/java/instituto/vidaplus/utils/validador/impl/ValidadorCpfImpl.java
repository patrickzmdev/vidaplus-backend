package instituto.vidaplus.utils.validador.impl;

import instituto.vidaplus.exception.genericas.CPFInvalidoException;
import instituto.vidaplus.utils.validador.ValidadorCpf;
import org.springframework.stereotype.Component;

@Component
public class ValidadorCpfImpl implements ValidadorCpf {
    @Override
    public void validarCpf(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            throw new CPFInvalidoException("CPF deve conter 11 digitos");
        }

        int soma = 0, peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * peso--;
        }
        int primeiroDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * peso--;
        }
        int segundoDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        if (cpf.charAt(9) - '0' != primeiroDigito || cpf.charAt(10) - '0' != segundoDigito) {
            throw new CPFInvalidoException("CPF inválido.");
        }
    }
}

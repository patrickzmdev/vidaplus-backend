package instituto.vidaplus.horario.enums;

import lombok.Getter;

@Getter
public enum DiasDaSemanaEnum {
    DOMINGO("Domingo"),
    SEGUNDA("Segunda-feira"),
    TERCA("Terça-feira"),
    QUARTA("Quarta-feira"),
    QUINTA("Quinta-feira"),
    SEXTA("Sexta-feira"),
    SABADO("Sábado");

    private final String descricao;

    DiasDaSemanaEnum(String descricao) {
        this.descricao = descricao;
    }
}

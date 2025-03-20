package instituto.vidaplus.horario.enums;

import lombok.Getter;

@Getter
public enum DiasDaSemanaEnum {
    DOMINGO("Domingo",7),
    SEGUNDA("Segunda-feira",1),
    TERCA("Terça-feira",2),
    QUARTA("Quarta-feira",3),
    QUINTA("Quinta-feira",4),
    SEXTA("Sexta-feira",5),
    SABADO("Sábado",6);

    private final String descricao;
    private final Integer valor;

    DiasDaSemanaEnum(String descricao, Integer valor) {
        this.descricao = descricao;
        this.valor = valor;
    }
}

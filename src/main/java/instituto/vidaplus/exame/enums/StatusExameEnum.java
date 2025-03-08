package instituto.vidaplus.exame.enums;

import lombok.Getter;

@Getter
public enum StatusExameEnum {
    SOLICITADO("Solicitado"),
    AGENDADO("Agendado"),
    REALIZADO("Realizado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusExameEnum(String descricao) {
        this.descricao = descricao;
    }
}

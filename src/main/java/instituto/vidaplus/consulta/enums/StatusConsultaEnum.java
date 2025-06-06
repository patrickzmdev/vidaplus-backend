package instituto.vidaplus.consulta.enums;

import lombok.Getter;

@Getter
public enum StatusConsultaEnum {
    AGENDADA("Agendada"),
    CONFIRMADA("Confirmada"),
    CANCELADA("Cancelada"),
    CONCLUIDA("Concluída"),
    NAO_COMPARECEU("Não compareceu"),
    EM_ANDAMENTO("Em andamento");

    private final String descricao;

    StatusConsultaEnum(String descricao) {
        this.descricao = descricao;
    }
}

package instituto.vidaplus.paciente.enums;

import lombok.Getter;

@Getter
public enum AlergiaEnum {
    PENICILINA("Penicilina"),
    AMOXICILINA("Amoxicilina"),
    DIPIRONA("Dipirona"),
    IBUPROFENO("Ibuprofeno"),
    PARACETAMOL("Paracetamol"),
    SULFAS("Sulfas"),
    ASPIRINA("Aspirina"),
    CODEINA("Codeína"),
    MORFINA("Morfina"),
    LACTOSE("Lactose"),
    GLUTEN("Glúten"),
    AMENDOIM("Amendoim"),
    MARISCOS("Mariscos"),
    POEIRA("Poeira"),
    POLEN("Pólen"),
    ACAROS("Ácaros"),
    LATEX("Látex"),
    ABELHAS("Abelhas"),
    FORMALDEIDO("Formaldeído"),
    OUTRA("Outra");

    private final String descricao;

    AlergiaEnum(String descricao) {
        this.descricao = descricao;
    }
}

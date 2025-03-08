package instituto.vidaplus.profissional.enums;

import lombok.Getter;

@Getter
public enum TipoProfissionalEnum {
    MEDICO("Médico"),
    ENFERMEIRO("Enfermeiro"),
    TECNICO_ENFERMAGEM("Técnico de Enfermagem"),
    FISIOTERAPEUTA("Fisioterapeuta"),
    NUTRICIONISTA("Nutricionista"),
    PSICOLOGO("Psicólogo"),
    FARMACEUTICO("Farmacêutico"),
    FONOAUDIOLOGO("Fonoaudiólogo"),
    ASSISTENTE_SOCIAL("Assistente Social"),
    TERAPEUTA_OCUPACIONAL("Terapeuta Ocupacional"),
    EDUCADOR_FISICO("Educador Físico"),
    BIOMEDICO("Biomédico"),
    ODONTOLOGO("Odontólogo"),
    OUTRO("Outro");

    private final String descricao;

    TipoProfissionalEnum(String descricao) {
        this.descricao = descricao;
    }
}

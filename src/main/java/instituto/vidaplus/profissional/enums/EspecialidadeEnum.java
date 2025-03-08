package instituto.vidaplus.profissional.enums;

import lombok.Getter;

@Getter
public enum EspecialidadeEnum {
    // Especialidades médicas
    CARDIOLOGIA("Cardiologia"),
    CLINICA_GERAL("Clínica Geral"),
    DERMATOLOGIA("Dermatologia"),
    ENDOCRINOLOGIA("Endocrinologia"),
    GASTROENTEROLOGIA("Gastroenterologia"),
    GINECOLOGIA("Ginecologia"),
    NEUROLOGIA("Neurologia"),
    OFTALMOLOGIA("Oftalmologia"),
    ORTOPEDIA("Ortopedia"),
    OTORRINOLARINGOLOGIA("Otorrinolaringologia"),
    PEDIATRIA("Pediatria"),
    PSIQUIATRIA("Psiquiatria"),
    UROLOGIA("Urologia"),

    // Outras áreas
    ENFERMAGEM_GERAL("Enfermagem Geral"),
    ENFERMAGEM_OBSTETRICA("Enfermagem Obstétrica"),
    FISIOTERAPIA_ORTOPEDICA("Fisioterapia Ortopédica"),
    FISIOTERAPIA_RESPIRATORIA("Fisioterapia Respiratória"),
    PSICOLOGIA_CLINICA("Psicologia Clínica"),

    // Outras especialidades
    OUTRA("Outra");

    private final String descricao;

    EspecialidadeEnum(String descricao) {
        this.descricao = descricao;
    }
}

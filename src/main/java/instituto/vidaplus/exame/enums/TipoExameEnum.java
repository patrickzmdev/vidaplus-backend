package instituto.vidaplus.exame.enums;

import lombok.Getter;

@Getter
public enum TipoExameEnum {
    HEMOGRAMA("Hemograma Completo"),
    GLICEMIA("Glicemia em Jejum"),
    COLESTEROL("Colesterol Total"),
    TRIGLICERIDES("Triglicerídeos"),
    TSH("TSH (Hormônio Tireoestimulante)"),
    T4_LIVRE("T4 Livre"),
    URINA("Exame de Urina"),
    FEZES("Exame de Fezes"),
    ELETROCARDIOGRAMA("Eletrocardiograma"),
    ECOCARDIOGRAMA("Ecocardiograma"),
    RADIOGRAFIA("Radiografia"),
    TOMOGRAFIA("Tomografia Computadorizada"),
    RESSONANCIA("Ressonância Magnética"),
    MAMOGRAFIA("Mamografia"),
    ULTRASSOM("Ultrassom"),
    DENSITOMETRIA_OSSEA("Densitometria Óssea"),
    TESTE_ERGOMETRICO("Teste Ergométrico"),
    ENDOSCOPIA("Endoscopia Digestiva Alta"),
    COLONOSCOPIA("Colonoscopia"),
    OUTRO("Outro Tipo de Exame");

    private final String descricao;

    TipoExameEnum(String descricao) {
        this.descricao = descricao;
    }

}

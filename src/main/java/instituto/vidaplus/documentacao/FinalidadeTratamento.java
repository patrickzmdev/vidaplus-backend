package instituto.vidaplus.documentacao;

import java.lang.annotation.*;

/**
 * Anotação para documentar a finalidade do tratamento de dados pessoais
 * conforme exigido pela LGPD.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FinalidadeTratamento {
    /**
     * Descrição da finalidade do tratamento de dados
     */
    String descricao() default "";

    /**
     * Base legal que justifica o tratamento de dados
     * Exemplos: consentimento, execução de contrato, interesse legítimo, etc.
     */
    String baseLegal() default "";
}

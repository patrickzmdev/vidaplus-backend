package instituto.vidaplus.documentacao.exemplos.paciente;

public class PacienteExamplo {
    public static final String EXEMPLO_PACIENTE_POST = """
            {
              "nome": "João Silva",
              "sexo": "MASCULINO",
              "cpf": "56346201042",
              "dataNascimento": "1980-01-15",
              "telefone": "(48)98765-4321",
              "email": "joao.silva@email.com",
              "cep": "78659970",
              "possuiPlanoDeSaude": true,
              "receberNotificacaoEmail": true,
            }""";

    public static final String EXEMPLO_PACIENTE_EDICAO = """
            {
                          "nome": "João Silva",
                          "sexo": "MASCULINO",
                          "cpf": "48462801010",
                          "dataNascimento": "1980-01-15",
                          "telefone": "(48)98765-4321",
                          "email": "joao.silva@email.com",
                          "cep": "59054490",
                          "possuiPlanoDeSaude": true,
                          "receberNotificacaoEmail": true
            }
            """;
    public static final String EXEMPLO_ERRO_VALIDACAO = """
            {
                          "nome": "João Silva",
                          "sexo": "MASCULINO",
                          "dataNascimento": "1980-01-15",
                          "telefone": "(48)98765-4321",
                          "email": "joao.silva@email.com",
                          "cep": "78659970",
                          "possuiPlanoDeSaude": true,
                          "receberNotificacaoEmail": true
            }
            """;
}

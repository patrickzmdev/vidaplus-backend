package instituto.vidaplus.documentacao.exemplos.profissionais;

public class ProfissionalExemplo {
    public static final String EXEMPLO_MEDICO_POST = """
            {
                    "nome": "Médico",
                    "sexo": "MASCULINO",
                    "cpf": "20806815086",
                    "dataNascimento": "2000-02-01",
                    "email": "testemedico2@teste.com",
                    "telefone": "48988173233",
                    "cep": "17607460",
                    "registro": "registro teste",
                    "tipoProfissional": "MEDICO",
                    "especialidade": "CARDIOLOGIA",
                    "permiteTelemedicina": true
            }\s""";

    public static final String EXEMPLO_MEDICO_EDICAO = """
            {
                     "nome": "Médico",
                     "sexo": "MASCULINO",
                     "cpf": "20806815086",
                     "dataNascimento": "2000-02-01",
                    "email": "testemedico2@teste.com",
                    "telefone": "48988173233",
                    "cep": "17607460",
                    "registro": "registro teste editado",
                    "tipoProfissional": "MEDICO",
                    "especialidade": "CARDIOLOGIA",
                    "permiteTelemedicina": true
            }
            """;
    public static final String EXEMPLO_ERRO_VALIDACAO = """
            {
                          "sexo": "MASCULINO",
                          "cpf": "20806815086",
                          "dataNascimento": "2000-02-01",
                          "email": "testemedico2@teste.com",
                          "telefone": "48988173233",
                          "cep": "17607460",
                          "registro": "registro teste editado",
                          "tipoProfissional": "MEDICO",
                          "especialidade": "CARDIOLOGIA",
                          "permiteTelemedicina": true
            }
            """;
}

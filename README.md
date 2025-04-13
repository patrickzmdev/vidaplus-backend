# Sistema de Gestão Hospitalar - Instituto VidaPlus

## Visão Geral
Sistema integrado para gestão hospitalar do Instituto VidaPlus, oferecendo controle de pacientes, profissionais, exames, internações, suprimentos e relatórios financeiros.

## Funcionalidades Principais

### Gestão de Pacientes
- Cadastro e atualização de dados de pacientes
- Histórico médico e prontuários
- Agendamento de consultas e exames

### Gestão de Profissionais
- Cadastro de médicos, enfermeiros, etc

### Gestão de Internações
- Cadastro de internações
- Associação com suprimentos utilizados
- Controle completo de leitos

### Gestão de Exames
- Cadastro de tipos de exames
- Controle de resultados
- Associação com suprimentos utilizados

### Gestão de Suprimentos
- Controle de estoque hospitalar
- Registro de consumo por exame

### Gestão Financeira
- Registro de receitas e despesas
- Relatórios financeiros por período
- Exportação em PDF e Excel
- Resumos financeiros

## Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Security
- JPA/Hibernate
- JasperReports (para relatórios PDF)
- Apache POI (para relatórios Excel)
- Swagger/OpenAPI para documentação da API
- JavaMailSander

## Requisitos do Sistema
- Java 17+
- Maven 
- PostgreSQL
- Mínimo 4GB RAM para execução

## Configuração e Instalação
1. Clone o repositório
2. Configure as propriedades do banco de dados em `application.properties`
3. Execute `mvn clean install` para compilar o projeto
4. É necessário um email e senha para conseguir enviar emails através do JavaMailSander. Em caso de duvidas esse é o link da documentação:https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mail/javamail/JavaMailSender.html

## Segurança
O acesso às APIs requer autenticação JWT. Diferentes níveis de permissão são baseados em roles:
- ADMIN: acesso completo ao sistema
- USER: somente acesso a alguns endpoints

package instituto.vidaplus.configuracao;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class SpringdocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("bearer-key");

        OpenAPI openAPI = new OpenAPI()
        .openapi("3.0.3")
        .info(new Info()
                .title("API Hospital Vida+")
                .version("1.0")
                .description("API para sistema hospitalar com conformidade LGPD")
                .contact(new Contact().name("Patrick")));
        openAPI.addExtension("x-lgpd-info", createLgpdInfo());

        List<SecurityRequirement> security = new ArrayList<>();
        security.add(securityRequirement);
        openAPI.setSecurity(security);

        Components components = new Components();
        components.addSecuritySchemes("bearer-key",
                new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
        openAPI.setComponents(components);
        System.setProperty("springdoc.api-docs.path", "/v3/api-docs");

        return openAPI;
    }

    private Map<String, Object> createLgpdInfo() {
        Map<String, Object> lgpdInfo = new HashMap<>();
        lgpdInfo.put("responsavel", "Hospital Vida+");
        lgpdInfo.put("contato_dpo", "dpo@hospital.com");
        lgpdInfo.put("finalidade", "Prestação de serviços médicos");
        lgpdInfo.put("armazenamento", "Dados armazenados em servidores no Brasil");
        lgpdInfo.put("retencao", "Dados médicos: 20 anos / Dados cadastrais: 5 anos após término do vínculo");
        return lgpdInfo;
    }
}
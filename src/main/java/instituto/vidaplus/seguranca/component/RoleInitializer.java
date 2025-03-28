package instituto.vidaplus.seguranca.component;

import instituto.vidaplus.seguranca.model.Role;
import instituto.vidaplus.seguranca.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role userRole = new Role();
            userRole.setNome("ROLE_USER");
            roleRepository.save(userRole);

            Role adminRole = new Role();
            adminRole.setNome("ROLE_ADMIN");
            roleRepository.save(adminRole);

            System.out.println("Roles initialized successfully.");

        }
    }
}

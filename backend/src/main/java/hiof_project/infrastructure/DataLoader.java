package hiof_project.infrastructure;

import hiof_project.domain.model.user_system.*;
import hiof_project.ports.out.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Automatisk opprettelse av data til database
@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Oppretter roller i rolle-tabell kun hvis det ikke eksisterer roller der allerede
    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Admin());
            roleRepository.save(new Developer());
            roleRepository.save(new Customer());
            System.out.println("Roller lagt til i databasen!");
        }
    }
}

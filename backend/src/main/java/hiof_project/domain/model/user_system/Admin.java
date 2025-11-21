package hiof_project.domain.model.user_system;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// Subklasse til Role for admin-rolle
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Role {

    public Admin() {
        super("ADMIN");
    }
}


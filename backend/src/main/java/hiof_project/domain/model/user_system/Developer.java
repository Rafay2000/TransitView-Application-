package hiof_project.domain.model.user_system;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// Subklasse til Role for developer-rolle
@Entity
@DiscriminatorValue("DEVELOPER")
public class Developer extends Role {

    public Developer() {
        super("DEVELOPER");
    }
}

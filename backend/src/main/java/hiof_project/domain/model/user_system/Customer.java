package hiof_project.domain.model.user_system;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends Role {

    public Customer() {
        super("CUSTOMER");
    }
}
package hiof_project.domain.model.user_system;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DEVELOPER")
public class Developer extends Role {

    public Developer() {
        super("DEVELOPER");
    }

    /*
    //Constructor for developer..
    public Developer(int userId, String userName, String firstName, String lastname, String role) {
        super(userId, userName, firstName, lastname, role);
    }

    //Display developer info for debugging, testing and verification
    @Override
    public String fullUserInfo() {
        return super.fullUserInfo();
    } */
}

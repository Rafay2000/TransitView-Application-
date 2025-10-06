package hiof_project.model.user_system;

public class Developer extends Role {

    //Constructor for developer
    public Developer(int userId, String userName, String firstName, String lastname, String role) {
        super(userId, userName, firstName, lastname, role);
    }

    //Display developer info for debugging, testing and verification
    @Override
    public String fullUserInfo() {
        return super.fullUserInfo();
    }
}

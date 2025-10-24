package java.hiof_project.domain.model.user_system;

public class Admin extends Role {

    private String password;

    //Constructor for admin with password
    public Admin(int userId, String userName, String password, String firstName, String lastname, String role) {
        super(userId, userName, firstName, lastname, role);
        this.password = password;
    }

    //Test constructor for admin with placeholder password
    public Admin(int userId, String userName, String firstName, String lastname, String role) {
        super(userId, userName, firstName, lastname, role);
        this.password = "placeholdertest123";
    }

    //Getter
    public String getPassword() {
        return password;
    }

    //Setter
    public void setPassword(String password) {
        this.password = password;
    }

    //Display admin info for debugging, testing and verification
    @Override
    public String fullUserInfo() {
        return super.fullUserInfo() +
                " - Admin{password=' " + password + '\'' + '}';
    }
}


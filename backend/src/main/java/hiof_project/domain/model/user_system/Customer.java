package hiof_project.domain.model.user_system;

public class Customer extends Role {

    private String email;
    private String password;

    //Constructor for customer with email and password.
    public Customer(int userId, String userName, String email, String password,
                    String firstName, String lastname, String role) {
        super(userId, userName, firstName, lastname, role);
        this.email = email;
        this.password = password;
    }

    //Test constructor for customer with placeholder email and password
    public Customer(int userId, String userName, String firstName, String lastname, String role) {
        super(userId, userName, firstName, lastname, role);
        this.email = "placeholdertest@email.com";
        this.password = "placeholdertest123";
    }

    //Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Display customer info for debugging, testing and verification
    @Override
    public String fullUserInfo() {
        return super.fullUserInfo() +
                " - Customer{email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

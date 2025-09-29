package hiof.project.model.user_system;

public abstract class Role {

    private int userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String role;

    //Default role constructor for "Customer", "Admin" and "Developer"
    public Role(int userId, String userName, String firstName, String lastname, String role) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastname;
        this.role = role;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    //Formats fullName and lastName in one line
    public String getFullName() {
        return firstName + " " + lastName;
    }


    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //Display full name with username and assigned role
    @Override
    public String toString() {
        return getFullName() + " | Brukernavn: " + userName + " - Rolle: " + role;
    }

    //Display full info on specific role made for subclasses for debugging and testing
    public String fullUserInfo() {
        return "Role{userId=" + userId +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

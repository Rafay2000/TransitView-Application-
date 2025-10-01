package hiof.project.model.user_system;

//Blueprint for authorization methods and checkers
public interface Authorization {

    void logInSystem(String username, String password);
    boolean accessLevelChecker(Role role);
    void giveAccessLevel(Role role);
    void revokeAccessLevel(Role role);
}

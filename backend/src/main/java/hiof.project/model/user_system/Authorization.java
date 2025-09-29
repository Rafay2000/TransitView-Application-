package hiof.project.model.user_system;

//Blueprint for authorization methods and checkers
public interface Authorization {

    boolean logInVerification(String username, String password);
    boolean accessLevelChecker();
    void giveAccessLevel();
    void revokeAccessLevel();
}

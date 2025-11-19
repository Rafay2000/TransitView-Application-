package hiof_project.domain.service;

public class UserValidator {

    public static void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Ugyldig e-post");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Passord må inneholde minst 8 tegn");

        if (!password.matches(".*[A-Z].*")) throw new IllegalArgumentException("Passord må inneholde minst én stor bokstav");

        if (!password.matches(".*[a-z].*")) throw new IllegalArgumentException("Passord må inneholde minst én liten bokstav");

        if (!password.matches(".*\\d.*")) throw new IllegalArgumentException("Passord må inneholde minst ett tall");
    }
}

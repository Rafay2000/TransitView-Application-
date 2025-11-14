package hiof_project.domain.exception;

public class RepositoryException extends Exception {
    // Exception håndtering for alle repository klasser.

    // Konstruktør med feilmelding og årsak
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    // Konstruktør med bare feilmelding
    public RepositoryException(String message) {
        super(message);
    }
}


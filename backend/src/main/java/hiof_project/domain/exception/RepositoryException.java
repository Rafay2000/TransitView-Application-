package hiof_project.domain.exception;

public class RepositoryException extends Exception {
    //Exception håndtering for alle repository klasser.
        public RepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }


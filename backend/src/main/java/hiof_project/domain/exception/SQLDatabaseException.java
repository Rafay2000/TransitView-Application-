package hiof_project.domain.exception;

//Exception håndtering for SQL database
public class SQLDatabaseException extends RuntimeException {
    public SQLDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

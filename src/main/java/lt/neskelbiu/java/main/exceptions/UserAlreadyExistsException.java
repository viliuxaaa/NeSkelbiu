package lt.neskelbiu.java.main.exceptions;

public class UserAlreadyExistsException extends IllegalArgumentException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

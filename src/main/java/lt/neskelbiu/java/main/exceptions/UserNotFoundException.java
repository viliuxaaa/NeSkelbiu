package lt.neskelbiu.java.main.exceptions;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException(String s) {
        super(s);
    }
}

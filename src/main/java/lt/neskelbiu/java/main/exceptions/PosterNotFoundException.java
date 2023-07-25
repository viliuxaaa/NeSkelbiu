package lt.neskelbiu.java.main.exceptions;

public class PosterNotFoundException extends IllegalArgumentException {
    public PosterNotFoundException(String message) {
        super(message);
    }
}

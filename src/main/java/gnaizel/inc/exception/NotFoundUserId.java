package gnaizel.inc.exception;

public class NotFoundUserId extends RuntimeException{
    public NotFoundUserId(String message) {
        super(message);
    }
}

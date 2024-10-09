package gnaizel.inc.exception;

public class NotFoundFilmId extends RuntimeException {
    public NotFoundFilmId(String message) {
        super(message);
    }
}

package sympo.exception;

public class CustomBadFileRequestException extends RuntimeException {
    public CustomBadFileRequestException(String message) {
        super(message);
    }
}

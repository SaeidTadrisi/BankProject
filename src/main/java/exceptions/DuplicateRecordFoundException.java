package exceptions;

public class DuplicateRecordFoundException extends RuntimeException{
    public DuplicateRecordFoundException(String message) {
        super(message);
    }
}

package tom.community.exception;

public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message=errorCode.getMessage();
    }

    public CustomizeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

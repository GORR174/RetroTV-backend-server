package net.catstack.retrotv.exception;

public class AlreadyExistsException extends BaseServiceException {
    private static final String MESSAGE = "Data is already exists error";
    private static final int CODE = 2;

    public AlreadyExistsException(final String message) {
        super(CODE, getExceptionMessage(message));
    }

    public AlreadyExistsException(final String message, final Throwable e) {
        super(CODE, getExceptionMessage(message), e);
    }

    private static String getExceptionMessage(final String message) {
        return message == null ? MESSAGE : MESSAGE + ": " + message;
    }
}

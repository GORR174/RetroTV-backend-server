package net.catstack.retrotv.exception;

public class DataDoesntExistsException extends BaseServiceException {
    private static final String MESSAGE = "Data doesn't exists error";
    private static final int CODE = 3;

    public DataDoesntExistsException(final String message) {
        super(CODE, getExceptionMessage(message));
    }

    public DataDoesntExistsException(final String message, final Throwable e) {
        super(CODE, getExceptionMessage(message), e);
    }

    private static String getExceptionMessage(final String message) {
        return message == null ? MESSAGE : MESSAGE + ": " + message;
    }
}

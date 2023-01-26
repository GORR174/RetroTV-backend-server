package net.catstack.retrotv.exception;

public class InternalServiceException extends BaseServiceException {
    private static final String MESSAGE = "Internal server error";
    private static final int CODE = 1;

    public InternalServiceException(final String message) {
        super(CODE, getExceptionMessage(message));
    }

    public InternalServiceException(final String message, final Throwable e) {
        super(CODE, getExceptionMessage(message), e);
    }

    private static String getExceptionMessage(final String message) {
        return message == null ? MESSAGE : MESSAGE + ": " + message;
    }
}

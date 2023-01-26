package net.catstack.retrotv.exception;

public class AuthException extends BaseServiceException {
    private static final String MESSAGE = "Authentication error";
    private static final int CODE = 4;

    public AuthException(final String message) {
        super(CODE, getExceptionMessage(message));
    }

    public AuthException(final String message, final Throwable e) {
        super(CODE, getExceptionMessage(message), e);
    }

    private static String getExceptionMessage(final String message) {
        return message == null ? MESSAGE : MESSAGE + ": " + message;
    }
}

package net.catstack.retrotv.exception;

import lombok.Getter;

@Getter
public class BaseServiceException extends RuntimeException {
    private final String message;
    private final int code;

    public BaseServiceException(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public BaseServiceException(int code, String message, Throwable e) {
        super(e);
        this.message = message;
        this.code = code;
    }
}

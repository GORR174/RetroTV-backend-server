package net.catstack.retrotv.handlers;

import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.dto.AdapterError;
import net.catstack.retrotv.dto.AdapterResponse;
import net.catstack.retrotv.exception.BaseServiceException;
import net.catstack.retrotv.exception.InternalServiceException;
import org.modelmapper.MappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseExceptionHandler {
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<AdapterResponse> handleBaseException(final Exception e) {
        return handleBaseException(new InternalServiceException(e.getMessage(), e));
    }

    @ExceptionHandler(value = { BaseServiceException.class })
    public ResponseEntity<AdapterResponse> handleBaseException(final BaseServiceException e) {
        log.error("Exception: {}", e.getMessage(), e);

        var response = new AdapterResponse<>();

        response.setStatus(1);
        response.setError(getAdapterErrorFromException(e));

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(value = { MappingException.class })
    public ResponseEntity<AdapterResponse> handleMapping(final Exception e) {
        log.error("Mapping error");

        if (e.getCause() instanceof BaseServiceException) {
            return handleBaseException((BaseServiceException) e.getCause());
        } else {
            return handleBaseException(e);
        }
    }

    private AdapterError getAdapterErrorFromException(final BaseServiceException e) {
        var adapterError = new AdapterError();

        adapterError.setCode(e.getCode());
        adapterError.setMessage(e.getMessage());

        return adapterError;
    }
}

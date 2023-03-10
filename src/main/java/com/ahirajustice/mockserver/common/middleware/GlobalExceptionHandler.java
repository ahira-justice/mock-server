package com.ahirajustice.mockserver.common.middleware;

import com.ahirajustice.mockserver.common.error.Error;
import com.ahirajustice.mockserver.common.error.ErrorResponse;
import com.ahirajustice.mockserver.common.exceptions.ApplicationDomainException;
import com.ahirajustice.mockserver.common.exceptions.ValidationException;
import com.ahirajustice.mockserver.common.exceptions.NotFoundException;
import com.ahirajustice.mockserver.common.exceptions.SystemErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);

        if (ex instanceof ApplicationDomainException) {
            return handleApplicationDomainException((ApplicationDomainException) ex);
        }
        else if (ex instanceof NoHandlerFoundException) {
            return handleNoHandlerFoundException((NoHandlerFoundException) ex);
        }
        else if (ex instanceof BindException) {
            return handleBindException((BindException) ex);
        }
        else {
            return handleUnknownException();
        }
    }

    private ResponseEntity<ErrorResponse> handleApplicationDomainException(ApplicationDomainException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), HttpStatus.valueOf(ex.getStatusCode()));
    }

    private ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String msg = String.format("Route %s %s does not exist", ex.getHttpMethod(), ex.getRequestURL());
        return handleApplicationDomainException(new NotFoundException(msg));
    }

    private ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        List<Error> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Error.create(error.getField(), error.getDefaultMessage(), error.getRejectedValue()))
                .collect(Collectors.toList());

        return handleApplicationDomainException(new ValidationException(errors));
    }

    private ResponseEntity<ErrorResponse> handleUnknownException() {
        return handleApplicationDomainException(new SystemErrorException());
    }

}

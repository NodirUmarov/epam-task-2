package com.epam.esm.aop;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.OperationDeniedException;
import com.epam.esm.model.log.EndPointErrorResponse;
import lombok.NonNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @NonNull ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                           @NonNull HttpHeaders headers,
                                                                           @NonNull HttpStatus status,
                                                                           @NonNull WebRequest request) {
        String errorMessage = "Malformed JSON request";
        return buildResponseEntity(generateApiError(ex, errorMessage, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponseEntity(generateApiError(ex, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(OperationDeniedException.class)
    protected ResponseEntity<Object> handleOperationDenied(OperationDeniedException ex) {
        return buildResponseEntity(generateApiError(ex, HttpStatus.NOT_ACCEPTABLE));
    }

    private ResponseEntity<Object> buildResponseEntity(EndPointErrorResponse apiErrorModel) {
        return new ResponseEntity<>(apiErrorModel, apiErrorModel.getStatus());
    }

    private EndPointErrorResponse generateApiError(Throwable ex, String message, HttpStatus httpStatus) {
        return new EndPointErrorResponse(httpStatus, message, ex);
    }

    private EndPointErrorResponse generateApiError(Throwable ex, HttpStatus httpStatus) {
        EndPointErrorResponse apiErrorModel = new EndPointErrorResponse(httpStatus);
        apiErrorModel.setMessage(ex.getMessage());
        return apiErrorModel;
    }
}
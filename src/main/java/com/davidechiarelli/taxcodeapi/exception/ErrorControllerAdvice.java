package com.davidechiarelli.taxcodeapi.exception;

import com.davidechiarelli.taxcodeapi.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {
    Logger log = LoggerFactory.getLogger(getClass());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCityFormatException.class)
    public final ResponseEntity<ErrorDTO> handleBadCityError(BadCityFormatException ex, final HttpServletRequest request) {
        ErrorDTO error = generateErrorDTO(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadDateFormatException.class)
    public final ResponseEntity<ErrorDTO> handleBadDateError(BadDateFormatException ex, final HttpServletRequest request) {
        ErrorDTO error = generateErrorDTO(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableDataException.class)
    public final ResponseEntity<ErrorDTO> handleUnprocessableData(UnprocessableDataException ex, final HttpServletRequest request) {
        ErrorDTO error = generateErrorDTO(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = generateErrorList(ex);
        ErrorDTO error = generateErrorDTO(ex, request, HttpStatus.BAD_REQUEST);
        error.setErrors(errorList);
        return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDTO error = generateErrorDTO(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Override
    protected ResponseEntity handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDTO error = generateErrorDTO(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<ErrorDTO>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDTO> handleGenericException(Exception ex, final HttpServletRequest request) {
        ErrorDTO error = generateErrorDTO(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<String> generateErrorList(BindException ex){
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField()+" "+fieldError.getDefaultMessage())
                .collect(Collectors.toList());
    }

    private ErrorDTO generateErrorDTO(Exception ex, HttpServletRequest request, HttpStatus status){
        ErrorDTO error = generateErrorDTO(ex, status);
        error.setPath(request.getServletPath());
        return error;
    }

    private ErrorDTO generateErrorDTO(Exception ex, WebRequest request, HttpStatus status){
        ErrorDTO error = generateErrorDTO(ex, status);
        error.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        return error;
    }

    private ErrorDTO generateErrorDTO(Exception ex, HttpStatus status){
        log.error(ex.getMessage());
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setMessage(ex.getMessage());
        error.setCode(status.value());
        error.setStatus(status);
        error.setTimestamp(LocalDateTime.now());
        return error;
    }
}

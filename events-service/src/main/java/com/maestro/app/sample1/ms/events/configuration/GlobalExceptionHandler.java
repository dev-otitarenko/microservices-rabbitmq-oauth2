package com.maestro.app.sample1.ms.events.configuration;

import com.maestro.app.utils.domains.APIErrorResponse;
import com.maestro.app.utils.exceptions.EntityRecordNotFound;
import com.maestro.app.utils.types.ErrorStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
        }
        APIErrorResponse apiError = new APIErrorResponse(ErrorStatus.ERROR, ex.getLocalizedMessage(), errors);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(apiError, headers,  HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityRecordNotFound.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        APIErrorResponse apiError = new APIErrorResponse(ErrorStatus.ERROR, ex.getLocalizedMessage(), ex.getLocalizedMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(apiError, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            UnsupportedEncodingException.class,
            IOException.class,
             ParserConfigurationException.class })
    public ResponseEntity<Object> handleUnsupportedEncodingException(UnsupportedEncodingException ex) {
        APIErrorResponse apiError = new APIErrorResponse(ErrorStatus.ERROR, ex.getLocalizedMessage(), ex.getLocalizedMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(apiError, headers, HttpStatus.FORBIDDEN);
    }
}

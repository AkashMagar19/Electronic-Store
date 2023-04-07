package com.htsspl.ElectronicStore.exception;

import com.htsspl.ElectronicStore.helper.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle Resource not found exception

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AppResponse> handleResourceNotFoundException(ResourceNotFoundException re) {
        logger.info("ResourceNotFoundException Handler invoked !!");
        AppResponse response = AppResponse.builder()
                .message(re.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(true).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle Method Argument Not Valid Exception

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException mx) {
        logger.info("MethodArgumentNotValidException Handler invoked !!");
        List<ObjectError> allErrors = mx.getBindingResult().getAllErrors();
        Map<String, Object> responce = new HashMap<>();

        allErrors.stream().forEach((objerror) -> {
            String defaultMessage = objerror.getDefaultMessage();
            String field = ((FieldError) objerror).getField();
            responce.put(field, defaultMessage);
        });
        return new ResponseEntity<Map<String, Object>>(responce, HttpStatus.BAD_REQUEST);
    }

    //handle Bad Api WException
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<AppResponse> handleBadApiRequest(BadApiRequest ex) {
        logger.info("Bad Api Request !!");
        AppResponse response = AppResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(true).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
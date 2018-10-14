package se.atg.service.harrykart.Exception;

import java.util.Date;

import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.undertow.util.BadRequestException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<ErrorDetails> ResourceNotFoundException(Exception ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ BadRequestException.class,
            IllegalArgumentException.class })
    public ResponseEntity<ErrorDetails> BadRequestException(Exception ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

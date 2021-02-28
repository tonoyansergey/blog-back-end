package am.egs.util.exception.handler;

import am.egs.model.ErrorDetails;
import am.egs.util.exception.InvalidUserNameOrPasswordException;
import am.egs.util.exception.NotUniqueCredentialsException;
import am.egs.util.exception.PersistFailureException;
import am.egs.util.exception.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@Component
@ControllerAdvice
public class CustomExceptionResolver extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @ExceptionHandler(PersistFailureException.class)
    protected ResponseEntity<ErrorDetails> handlePersistFailureException(PersistFailureException ex) {
        final ErrorDetails errorDetails = new ErrorDetails(new Date(), "Internal error",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleRecordNotFoundException(RecordNotFoundException ex) {
        final ErrorDetails errorDetails = new ErrorDetails(new Date(), "Records not found",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotUniqueCredentialsException.class)
    protected ResponseEntity<ErrorDetails> handleUnavailableCredentialsException(NotUniqueCredentialsException ex) {
        final ErrorDetails errorDetails = new ErrorDetails(new Date(), "Credentials already exist", ex.getMessage(), ex.getCredentialsMap()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserNameOrPasswordException.class)
    protected ResponseEntity<ErrorDetails> handleInvalidUserNameOrPasswordException (InvalidUserNameOrPasswordException ex) {
        final ErrorDetails errorDetails = new ErrorDetails(new Date(), "Wrong username or password",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}

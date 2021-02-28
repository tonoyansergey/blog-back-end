package am.egs.util.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvalidUserNameOrPasswordException extends RuntimeException {

    public InvalidUserNameOrPasswordException(String message) {
        super(message);
    }

    public InvalidUserNameOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserNameOrPasswordException(Throwable cause) {
        super(cause);
    }

    protected InvalidUserNameOrPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

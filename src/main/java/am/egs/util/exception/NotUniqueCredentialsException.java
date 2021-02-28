package am.egs.util.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotUniqueCredentialsException extends RuntimeException {

    private Map<String, Boolean> credentialsMap;

    public NotUniqueCredentialsException(Map credentialsMap) {
        this.credentialsMap = credentialsMap;
    }

    public NotUniqueCredentialsException(String message) {
        super(message);
    }

    public NotUniqueCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueCredentialsException(Throwable cause) {
        super(cause);
    }

    public NotUniqueCredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}


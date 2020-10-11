package app.challenge;

import org.springframework.security.core.AuthenticationException;

public class DniNotFoundException extends AuthenticationException {

    public DniNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public DniNotFoundException(String msg) {
        super(msg);
    }
}

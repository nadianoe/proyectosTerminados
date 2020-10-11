package app.challenge;

import org.springframework.security.core.AuthenticationException;

public class IsNotAdminException extends AuthenticationException {

    public IsNotAdminException(String msg, Throwable t) {
        super(msg, t);
    }

    public IsNotAdminException(String msg) {
        super(msg);
    }
}

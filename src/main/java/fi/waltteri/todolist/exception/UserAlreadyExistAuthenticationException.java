package fi.waltteri.todolist.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistAuthenticationException extends AuthenticationException {

    public UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }
}
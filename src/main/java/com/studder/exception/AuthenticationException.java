package com.studder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -53505583826191693L;

	public AuthenticationException(String message) {
		super(message);
	}
	
	public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public AuthenticationException(Throwable cause) {
        super(cause);
    }
	
}

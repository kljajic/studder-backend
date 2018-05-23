package com.studder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataBaseManipulationException extends RuntimeException {

	private static final long serialVersionUID = -53505583826191693L;

	public DataBaseManipulationException(String message) {
		super(message);
	}
	
	public DataBaseManipulationException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public DataBaseManipulationException(Throwable cause) {
        super(cause);
    }
	
}

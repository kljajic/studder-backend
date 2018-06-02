package com.studder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileManipulationException extends RuntimeException {

	private static final long serialVersionUID = -53505583826191693L;

	public FileManipulationException(String message) {
		super(message);
	}
	
	public FileManipulationException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public FileManipulationException(Throwable cause) {
        super(cause);
    }
	
}

package com.pkrok.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class MailExistException extends RuntimeException{
    public MailExistException(String message) {
        super(message);
    }
}

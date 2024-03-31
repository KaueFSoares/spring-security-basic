package br.com.kauesoares.simplespringsecurityproject.project.messages.exception;

import br.com.kauesoares.simplespringsecurityproject.project.messages.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ServiceException{

    public UnauthorizedException(Messages message, String... args) {
        super(message, args);
    }
}

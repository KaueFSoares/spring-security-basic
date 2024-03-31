package br.com.kauesoares.simplespringsecurityproject.project.messages.exception;

import br.com.kauesoares.simplespringsecurityproject.project.messages.MessageFactory;
import br.com.kauesoares.simplespringsecurityproject.project.messages.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ServiceException {

    public BadRequestException(Messages message) {
        super(MessageFactory.getMessage(message));
    }

    public BadRequestException(Messages message, String... args) {
        super(MessageFactory.getMessage(message, args));
    }

    public BadRequestException(String message) {
        super(message);
    }
}

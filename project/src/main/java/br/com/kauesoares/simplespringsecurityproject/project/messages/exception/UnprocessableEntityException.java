package br.com.kauesoares.simplespringsecurityproject.project.messages.exception;

import br.com.kauesoares.simplespringsecurityproject.project.messages.MessageFactory;
import br.com.kauesoares.simplespringsecurityproject.project.messages.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends ServiceException {

    public UnprocessableEntityException(Messages message) {
        super(MessageFactory.getMessage(message));
    }

    public UnprocessableEntityException(Messages message, String... args) {
        super(MessageFactory.getMessage(message, args));
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}

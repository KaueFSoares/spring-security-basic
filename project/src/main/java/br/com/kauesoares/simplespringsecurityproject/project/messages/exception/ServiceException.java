package br.com.kauesoares.simplespringsecurityproject.project.messages.exception;

import br.com.kauesoares.simplespringsecurityproject.project.messages.MessageFactory;
import br.com.kauesoares.simplespringsecurityproject.project.messages.Messages;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Messages message) {
        super(MessageFactory.getMessage(message));
    }

    public ServiceException(Messages message, String... args) {
        super(MessageFactory.getMessage(message, args));
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

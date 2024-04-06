package br.com.kauesoares.simplespringsecurityproject.project.dto;

import br.com.kauesoares.simplespringsecurityproject.project.messages.MessageFactory;
import br.com.kauesoares.simplespringsecurityproject.project.messages.Messages;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
    private String message;
    private T data;

    public Response(Messages message) {
        this.message = MessageFactory.getMessage(message);
    }

    public Response(Messages message, String... messageArgs) {
        this.message = MessageFactory.getMessage(message, messageArgs);
    }

    public Response(Messages message, T data) {
        this.message = MessageFactory.getMessage(message);
        this.data = data;
    }

    public Response(Messages message, T data, String... messageArgs) {
        this.message = MessageFactory.getMessage(message, messageArgs);
        this.data = data;
    }

    public Response(T data) {
        this.message = MessageFactory.getMessage(Messages.SUCCESS);
        this.data = data;
    }

    public static <T> Response<T> with(T data) {
        return new Response<>(data);
    }

    public static <T> Response<T> with(T data, Messages message) {
        return new Response<>(message, data);
    }

    public static <T> Response<T> with(Messages message) {
        return new Response<>(message);
    }
}

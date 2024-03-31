package br.com.kauesoares.simplespringsecurityproject.project.messages;

import br.com.kauesoares.simplespringsecurityproject.project.config.ContextProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageFactory {
    private static MessageSource messageSource;

    private static MessageSource getMessageSource() {

        if (messageSource == null)
            messageSource = ContextProvider.getBean("messageSource", MessageSource.class);

        return messageSource;
    }

    public static String getMessage(Messages message) {
        return getMessageSource().getMessage(message.toString(), null, LocaleContextHolder.getLocale());
    }

    public static String getMessage(Messages message, String... args) {
        return getMessageSource().getMessage(message.toString(), args, LocaleContextHolder.getLocale());
    }

    public static String getLogMessage(Messages message, String where, String what) {
        return getMessageSource().getMessage(message.toString(), new String[]{where, what}, LocaleContextHolder.getLocale());
    }
}

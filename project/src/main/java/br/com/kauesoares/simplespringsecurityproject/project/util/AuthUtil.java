package br.com.kauesoares.simplespringsecurityproject.project.util;

import br.com.kauesoares.simplespringsecurityproject.project.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static User getLoggedUser() {
        try {
            return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUserName() {
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null
                || "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

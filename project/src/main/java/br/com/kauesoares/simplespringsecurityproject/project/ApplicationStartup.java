package br.com.kauesoares.simplespringsecurityproject.project;

import br.com.kauesoares.simplespringsecurityproject.project.config.properties.AuthProperties;
import br.com.kauesoares.simplespringsecurityproject.project.domain.Role;
import br.com.kauesoares.simplespringsecurityproject.project.model.User;
import br.com.kauesoares.simplespringsecurityproject.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final UserService userService;
    private final AuthProperties authProperties;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.initDatabase();
    }

    private void initDatabase() {

        if (this.userService.findAll().isEmpty()) {
            HashSet<Role> adminRole = new HashSet<>(Set.of(Role.ADMIN));
            this.userService.save(
                    User.builder()
                            .username(authProperties.getAdminUsername())
                            .password(this.passwordEncoder.encode(authProperties.getAdminPassword()))
                            .roles(adminRole)
                            .build()
            );

            HashSet<Role> userRole = new HashSet<>(Set.of(Role.USER));
            this.userService.save(
                    User.builder()
                            .username(authProperties.getUserUsername())
                            .password(this.passwordEncoder.encode(authProperties.getUserPassword()))
                            .roles(userRole)
                            .build()
            );
        }
    }
}

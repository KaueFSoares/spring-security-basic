package br.com.kauesoares.simplespringsecurityproject.project.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "Hello Admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String user() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "Hello User";
    }

}

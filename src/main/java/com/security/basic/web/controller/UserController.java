package com.security.basic.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @PreAuthorize("hasAnyAuthority('READ','WRITE')")
    @GetMapping("/read")
    public String read() {
        return "/read";
    }

    @PreAuthorize("hasAnyAuthority('WRITE')")
    @GetMapping("/write")
    public String write() {
        return "/write";
    }
}

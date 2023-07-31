package com.transport.controller;

import com.transport.api.dto.AuthenticationDto;
import com.transport.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public String signIn(@RequestBody AuthenticationDto authenticationRequest) {
        return authenticationService.createAuthenticationToken(authenticationRequest);
    }

    @GetMapping("/home")
    public String home() {
        return "You have no rights to enter this page";
    }
}

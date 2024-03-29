package com.security.jwtservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AutthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
         return authenticationService.register(registerRequest);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AutthenticationResponse> authenticate(@RequestBody AuthenticationRequest autheticationRequest) {
         return authenticationService.authenticate(autheticationRequest);

    }





}

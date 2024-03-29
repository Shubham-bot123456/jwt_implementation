package com.security.jwtservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/register")
    public ResponseEntity<AutthenticationResponse> autthenticate(@RequestBody AuthenticationRequest autheticationRequest) {
         return authenticationService.authenticate(autheticationRequest);

    }


}

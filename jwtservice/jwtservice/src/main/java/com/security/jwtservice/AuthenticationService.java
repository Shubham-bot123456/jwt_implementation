package com.security.jwtservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UsesRepository userRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    public ResponseEntity<AutthenticationResponse> register(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstname(registerRequest.getFirstName());
        user.setLastname(registerRequest.getLastName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());
        user.setEmailId(registerRequest.getEmailId());
        user.setRole(Role.USER);
        userRepository.save(user);
        String jwtToken = jwtService.generateTokenOnlyWithUserDetails(user);
        AutthenticationResponse authenticationResponse = new AutthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return ResponseEntity.ok(authenticationResponse);
    }
@Autowired
private AuthenticationProvider authenticationProvider;
    public ResponseEntity<AutthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        // this is also another way of doint it
        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
        );
        UserDetails userDetails = userRepository.findByUserName(authenticationRequest.getUsername()).orElseThrow(() -> new RuntimeException("Object not found in the database !"));
        String jwtToken = jwtService.generateTokenOnlyWithUserDetails(userDetails);
        AutthenticationResponse authenticationResponse = new AutthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return ResponseEntity.ok(authenticationResponse);
    }
}

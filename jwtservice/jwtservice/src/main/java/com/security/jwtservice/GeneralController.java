package com.security.jwtservice;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/general")
@RequiredArgsConstructor
public class GeneralController {

    private final UsesRepository userRepository;

    @GetMapping("/checking")
    public ResponseEntity<String>  check(){
       return ResponseEntity.ok("Everything is ok ");
   }

    @GetMapping("/userdetailsfromid/{id}")
    public ResponseEntity<User> getUserDetailsFromId(@PathVariable("id") Integer id){
        User user= userRepository.findById(id).orElseThrow(()->new RuntimeException("There was not entry in database for the give user"));
        return ResponseEntity.ok(user);
    }
}

package com.security.jwtservice;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/general")
public class GeneralController {


    @GetMapping("/checking")
   public ResponseEntity<String>  check(){
       return ResponseEntity.ok("Everything is ok ");
   }








}

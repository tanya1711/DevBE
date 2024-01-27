package org.example.controller;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public ResponseEntity<?> userLogin(@RequestParam String email, @RequestParam String password){
        System.out.println("from controller "+email);
        User byEmail = userRepository.findByEmail(email);
        System.out.println(byEmail.getRole());
        return ResponseEntity.ok(byEmail);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<?> userPage(@RequestParam String email, @RequestParam String password){
        System.out.println("from controller "+email);
        User byEmail = userRepository.findByEmail(email);
        System.out.println(byEmail.getRole());
        return ResponseEntity.ok(byEmail);
    }
}


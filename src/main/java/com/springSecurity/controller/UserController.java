package com.springSecurity.controller;

import com.springSecurity.model.Customer;
import com.springSecurity.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            String hasPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hasPwd);
            Customer savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId()>0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Exception occured " + e.getMessage());
        }

    }
}

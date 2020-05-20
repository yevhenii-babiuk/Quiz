package com.qucat.quiz.controllers;

import com.qucat.quiz.config.JwtTokenUtil;
import com.qucat.quiz.repositories.dto.jwt.JwtRequest;
import com.qucat.quiz.repositories.dto.jwt.JwtResponse;
import com.qucat.quiz.services.JwtUserDetailsService;
import com.qucat.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/login")
public class LoginController {
    @Autowired
    private UserService uService;

    @Autowired
    private JwtUserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {

        uService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}

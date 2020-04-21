package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.jwt.JwtRequest;
import com.qucat.quiz.repositories.jwt.JwtResponse;
import com.qucat.quiz.services.JwtUserDetailsService;
import com.qucat.quiz.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    //    @PostMapping
    //    public ResponseEntity<?> loginUser(@RequestBody JwtRequest authenticationRequest) throws Exception {
    //        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    //
    //        final UserDetails userDetails = jwtInMemoryUserDetailsService
    //                .loadUserByUsername(authenticationRequest.getUsername());
    //
    //        final String token = jwtTokenUtil.generateToken(userDetails);
    //
    //        return ResponseEntity.ok(new JwtResponse(token));
    //    }

    //    @RequestMapping(value = "api/v1/login", method = RequestMethod.POST)
    //    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
    //            throws Exception {
    //
    //        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    //
    //        final UserDetails userDetails = jwtInMemoryUserDetailsService
    //                .loadUserByUsername(authenticationRequest.getUsername());
    //
    //        final String token = jwtTokenUtil.generateToken(userDetails);
    //
    //        return ResponseEntity.ok(new JwtResponse(token));
    //    }

    //TODO move to the service level
    private void authenticate(String username, String password) throws Exception {
        //        User user = usDao.getUserByLoginAndPassword(username, password);
        //
        //        userService.loginUser(user.getLogin(), user.getPassword());

        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

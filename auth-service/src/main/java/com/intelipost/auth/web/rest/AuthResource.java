package com.intelipost.auth.web.rest;

import java.util.HashMap;
import java.util.Map;

import com.intelipost.auth.repository.UserRepository;
import com.intelipost.auth.security.TokenProvider;
import com.intelipost.auth.service.dto.AuthUserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthUserDto user) {
        
        try {

            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));            
                   
            
            String token = tokenProvider.createToken(user.getUsername(),
                    this.userRepository.findByUsername(user.getUsername())
                            .orElseThrow(
                                    () -> new UsernameNotFoundException("Username " + user.getUsername() + "not found"))
                            .getRoles());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", user.getUsername());
            model.put("token", token);

            HttpHeaders headers = new HttpHeaders();

            return ResponseEntity.ok().headers(headers).body(model);           

        } catch (AuthenticationException ex) {            
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }     
        
        
    }
}
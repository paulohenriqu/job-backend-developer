package com.intelipost.auth;

import java.util.Arrays;

import com.intelipost.auth.domain.User;
import com.intelipost.auth.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(DataBaseInitializer.class);


    @Override
    public void run(String... args) throws Exception {

        /*
        logger.info("Initializing sample user");
        User user = new User("admin", this.passwordEncoder.encode("password"), Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        this.userRepository.save(user);
        */

    }
}
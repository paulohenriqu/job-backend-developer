package com.intelipost.profile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.intelipost.profile.domain.Profile;
import com.intelipost.profile.repository.ProfileRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitializer implements CommandLineRunner {

    @Autowired
    ProfileRepository profileRepository;

    Logger logger = LoggerFactory.getLogger(DataBaseInitializer.class);

    @Override
    public void run(String... args) throws Exception {

        // em um caso de uso mais complexo o correto seria utilizar uma ferramenta como
        // o Liquibase
        List<Profile> check = profileRepository.findAll();
        if (check.size() > 0) {
            logger.info("Database already initialized");
        } else {

            logger.info("Initializing sample profiles");

            LocalDate birthday = LocalDate.of(1992, 9, 19);

            Profile profile = new Profile(1l, "paulohenriqu@hotmail.com", "Paulo", "de Siqueira",
                    "http://phsiqueira.com/wp-content/uploads/2018/08/profile-wp.png", birthday);
            this.profileRepository.save(profile);

            Profile profile2 = new Profile(2l, "siqueira.pauloh@gmail.com", "Henrique", "de Siqueira",
                    "http://phsiqueira.com/wp-content/uploads/2018/08/profile-wp.png", birthday);
            this.profileRepository.save(profile);
        }

    }
}
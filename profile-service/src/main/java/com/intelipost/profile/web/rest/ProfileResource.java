package com.intelipost.profile.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.intelipost.profile.domain.Profile;
import com.intelipost.profile.repository.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ProfileResource {

      
    @Autowired
    private ProfileRepository profileRepository;    

    

   
    @PostMapping("/profile")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) throws URISyntaxException {
              
        Profile result = profileRepository.save(profile);

        HttpHeaders headers = new HttpHeaders();

            return ResponseEntity.created(new URI("/api/profile/" + result.getId())).headers(headers).body(result);       
        
    }
  
    @PutMapping("/profile")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) throws URISyntaxException {
               
        Profile result = profileRepository.save(profile);
        
        HttpHeaders headers = new HttpHeaders();
        return ResponseEntity.ok()
            .headers(headers)
            .body(result);
    }

   
    @GetMapping("/profile")
    public ResponseEntity<List<Profile>> getAllProfiles(Pageable pageable) {       
        Page<Profile> page = profileRepository.findAll(pageable);
        HttpHeaders headers = new HttpHeaders();
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

   
    @GetMapping("/profile/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long id) {
       
        Optional<Profile> profile = profileRepository.findById(id);

        HttpHeaders headers = new HttpHeaders();

        if(profile.isPresent()){            
            return ResponseEntity.ok().headers(headers).body(profile.get());
        }else{
            return ResponseEntity.notFound().headers(headers).build();
        }       
    }

    
    @GetMapping("/profile/user/{userId}")
    public ResponseEntity<Profile> getUserProfile(@PathVariable Long userId) {
       
        Optional<Profile> profile = profileRepository.findByuserId(userId);

        HttpHeaders headers = new HttpHeaders();

        if(profile.isPresent()){            
            return ResponseEntity.ok().headers(headers).body(profile.get());
        }else{
            return ResponseEntity.notFound().headers(headers).build();
        }       
    }

    
    @DeleteMapping("/profile/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
       
        profileRepository.deleteById(id);
        HttpHeaders headers = new HttpHeaders();
        return ResponseEntity.ok().headers(headers).build();
    }  

}

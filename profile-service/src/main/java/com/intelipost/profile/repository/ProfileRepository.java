package com.intelipost.profile.repository;

import java.util.Optional;

import com.intelipost.profile.domain.Profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    Optional<Profile> findByuserId(Long userId);
    
}
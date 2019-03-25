package com.intelipost.profile.repository;

import com.intelipost.profile.domain.Profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
}
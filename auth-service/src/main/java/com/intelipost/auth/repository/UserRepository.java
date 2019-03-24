package com.intelipost.auth.repository;

import java.util.Optional;

import com.intelipost.auth.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
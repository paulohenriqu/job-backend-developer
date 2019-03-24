package com.intelipost.auth.repository;

import com.intelipost.auth.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
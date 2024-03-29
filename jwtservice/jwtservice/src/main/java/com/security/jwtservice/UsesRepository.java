package com.security.jwtservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsesRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailId(String emailId);
}

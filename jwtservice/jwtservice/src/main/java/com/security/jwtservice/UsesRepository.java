package com.security.jwtservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsesRepository extends JpaRepository<User, Integer> {
    @Query("select urs from User urs where urs.username=?1")
    Optional<User> findByUserName(String emailId);
}

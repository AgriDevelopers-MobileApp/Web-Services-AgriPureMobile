package com.agripure.agripurebackend.security.repository;

import com.agripure.agripurebackend.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findById(Long userId);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}

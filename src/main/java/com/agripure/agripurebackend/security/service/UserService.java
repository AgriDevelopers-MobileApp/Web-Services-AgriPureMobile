package com.agripure.agripurebackend.security.service;

import com.agripure.agripurebackend.security.repository.UserRepository;
import com.agripure.agripurebackend.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;


    public Optional<User> findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }
    public boolean existsByUserName(String userName){
        return userRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
}

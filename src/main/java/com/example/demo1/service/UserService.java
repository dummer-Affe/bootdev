package com.example.demo1.service;

import com.example.demo1.entity.UserInfo;
import com.example.demo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public boolean addUserByUser (UserInfo user){
        Optional<UserInfo> tempUser = repository.findByEmail(user.getEmail());
        if(tempUser.isPresent()){
            return false;
        }
        else{
            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
            return true;
        }
    }

    public int getUserOtp (String email){
        Optional<UserInfo> user = repository.findByEmail(email);
        if ( user.isPresent()){
            int userOtp = user.get().getOtp();
            return userOtp;
        }
        else {
            return 0;
        }
    }
}
package com.example.demo1.config;

import com.example.demo1.entity.UserInfo;
import com.example.demo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> user = repository.findByEmail(email);
        return user.map(UserInfoUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User Not Found"+email));

    }
    //@Override
    //public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //    Optional<UserInfo> user = repository.findByUsername(username);
    //    return user.map(UserInfoUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User Not Found"+username));
//
    //}
}

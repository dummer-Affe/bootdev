package com.example.demo1.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.demo1.entity.UserInfo;
import com.example.demo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TwoStepVerService {
    @Autowired
    private UserRepository userRepo;


    @Autowired
    JavaMailSender javaMailSender;


    public boolean generateOtp(UserInfo user) {

      try {
          int randomPIN = (int) (Math.random() * 900000) + 100000;
          user.setOtp(randomPIN);
          userRepo.save(user);
          SimpleMailMessage msg = new SimpleMailMessage();
          msg.setFrom("fromemail@gmail.com"); // input the senders email ID
          msg.setTo("turanlibrahimozan@gmail.com");

          msg.setSubject("Pass It 2FA");
          msg.setText("Hello, " +user.getEmail() + "\n\n" +"Your Login OTP :" + randomPIN + "\n\nPlease Verify. \n\n"+"Best Regards \n\n"+"Jahnavi");

          javaMailSender.send(msg);

          return true;
      }catch (Exception e) {
          e.printStackTrace();
          return false;
      }
    }
}

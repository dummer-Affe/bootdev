package com.example.demo1.controller;

import com.example.demo1.dto.AuthRequest;
import com.example.demo1.dto.OtpCheck;
import com.example.demo1.entity.UserInfo;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.service.TwoStepVerService;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TwoStepVerService twoStepVerService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/public/add")
    public Map<String,String> addUserByUser(@RequestBody UserInfo user){
        userService.addUserByUser(user);
        Map<String, String> response = new HashMap<>();
        response.put("Status", "Saved");
        return response;
    }

    @GetMapping("/user/checkOtp")
    public ResponseEntity checkUserOtp(@RequestBody OtpCheck credentials){
       int validationOtp = userService.getUserOtp(credentials.getEmail());
       int givenOtp = credentials.getOtp();
        Map<String, String> response = new HashMap<>();
        if(givenOtp == validationOtp ){
            response.put("data", "true");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            response.put("data", "error");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }



    @GetMapping("/public/login")
    public ResponseEntity AuthAndGenerateToken(@RequestBody AuthRequest authRequest){
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("kekw6");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()));
                    System.out.println("kekw3");
                    response.put("data","true");
                    response.put("status","success");
                    response.put("message","success");
                     twoStepVerService.generateOtp(
                         userRepo.findByEmail(authRequest.getEmail()).get());
                    return new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch(Exception e) {
            System.out.println("kekw7");
            response.put("data","false");
            response.put("status","error");
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}

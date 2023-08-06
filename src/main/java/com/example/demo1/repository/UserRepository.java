package com.example.demo1.repository;

import com.example.demo1.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo,Integer> {

    Optional<UserInfo> findByUsername(String username);
    @Query(value = "SELECT * FROM user_info WHERE email=? LIMIT 1",nativeQuery = true)
    Optional<UserInfo> findByEmail(String email);
}

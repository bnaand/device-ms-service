package com.akenza.devicemsdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth/users")
public class UserResource {

    @Autowired
    UserDetailsManager manager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<String> add(@RequestParam("username") String username, @RequestParam("password") String password) {
        UserDetails user = User.withUsername(username)
                .password(passwordEncoder.encode(password)).roles("USER").build();
        manager.createUser(user);
        return ResponseEntity.ok("ok");
    }
}

package com.davidechiarelli.taxcodeapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements UserDetailsService {
    private List<User> users;

    @Autowired
    public AuthenticationServiceImpl() {
        List<GrantedAuthority> ga = Arrays.asList(new SimpleGrantedAuthority("API"));
        users = Arrays.asList(
                new User("user1", "pass1", ga),
                new User("user2", "pass2", ga),
                new User("user3", "pass3", ga)
        );
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s doesn't exist.", username)));
    }
}


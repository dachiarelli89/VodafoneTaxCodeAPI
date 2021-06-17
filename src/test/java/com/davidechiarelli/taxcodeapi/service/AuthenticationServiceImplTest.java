package com.davidechiarelli.taxcodeapi.service;

import com.davidechiarelli.taxcodeapi.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AuthenticationServiceImplTest {
    AuthenticationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AuthenticationServiceImpl();
    }

    @Test
    void testSuccess(){
        UserDetails user = service.loadUserByUsername("user1");

        assertThat(user).isNotNull();
    }

    @Test
    void testError_notfound(){
        assertThatExceptionOfType(org.springframework.security.core.userdetails.UsernameNotFoundException.class).isThrownBy(
                ()->service.loadUserByUsername("user5")
        );
    }
}

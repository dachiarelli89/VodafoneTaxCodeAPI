package com.davidechiarelli.taxcodeapi.rest;

import com.davidechiarelli.taxcodeapi.security.TokenProvider;
import com.davidechiarelli.taxcodeapi.service.AuthenticationService;
import com.davidechiarelli.taxcodeapi.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {
    private AuthController apiController;
    private AuthenticationService service = mock(AuthenticationServiceImpl.class);
    private TokenProvider tokenProvider = mock(TokenProvider.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        apiController = new AuthController(tokenProvider, service);
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    void calculateTaxCode200() throws Exception {
        when(service.loadUserByUsername("user1")).thenReturn(new org.springframework.security.core.userdetails.User("user1", "pass1", Arrays.asList(new SimpleGrantedAuthority("API"))));

        String jsonRequest = "{\n" +
                "  \"username\": \"user1\",\n" +
                "  \"password\": \"pass1\"" +
                "}";

        mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void calculateTaxCode404_userIsNull() throws Exception {
        when(service.loadUserByUsername("user1")).thenReturn(null);

        String jsonRequest = "{\n" +
                "  \"username\": \"user1\",\n" +
                "  \"password\": \"pass1\"" +
                "}";

        assertThatExceptionOfType(NestedServletException.class)
                .isThrownBy(() ->
                        mockMvc.perform(post("/auth/token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                                .accept(MediaType.APPLICATION_JSON))
                );
    }

    @Test
    void calculateTaxCode404_wrongPassword() throws Exception {
        when(service.loadUserByUsername("user1")).thenReturn(new org.springframework.security.core.userdetails.User("user1", "pass1", Arrays.asList(new SimpleGrantedAuthority("API"))));

        String jsonRequest = "{\n" +
                "  \"username\": \"user1\",\n" +
                "  \"password\": \"pass2\"" +
                "}";

        assertThatExceptionOfType(NestedServletException.class)
                .isThrownBy(() ->
                        mockMvc.perform(post("/auth/token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                                .accept(MediaType.APPLICATION_JSON))
                );
    }


}

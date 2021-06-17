package com.davidechiarelli.taxcodeapi.rest;

import com.davidechiarelli.taxcodeapi.dto.TokenDTO;
import com.davidechiarelli.taxcodeapi.dto.UserAccountDTO;
import com.davidechiarelli.taxcodeapi.security.TokenProvider;
import com.davidechiarelli.taxcodeapi.service.impl.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    private TokenProvider tokenProvider;
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    AuthController(TokenProvider tokenProvider, AuthenticationServiceImpl authenticationService) {
        this.tokenProvider = tokenProvider;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/auth/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDTO> generateToken(@Valid @RequestBody UserAccountDTO userAccountDTO) {
        UserDetails loadedUser = authenticationService.loadUserByUsername(userAccountDTO.getUsername());

        if (loadedUser != null && loadedUser.getPassword().equals(userAccountDTO.getPassword())) {
            return ResponseEntity.ok(
                    new TokenDTO(tokenProvider.createToken(new UsernamePasswordAuthenticationToken(
                            loadedUser.getUsername(),
                            loadedUser.getUsername() + "RANDOMSTRING",
                            loadedUser.getAuthorities()))));
        } else {
            throw new UsernameNotFoundException("User or password are wrong");
        }
    }
}

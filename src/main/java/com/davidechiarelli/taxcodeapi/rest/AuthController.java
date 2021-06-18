package com.davidechiarelli.taxcodeapi.rest;

import com.davidechiarelli.taxcodeapi.dto.ErrorDTO;
import com.davidechiarelli.taxcodeapi.dto.TokenDTO;
import com.davidechiarelli.taxcodeapi.dto.UserAccountDTO;
import com.davidechiarelli.taxcodeapi.security.TokenProvider;
import com.davidechiarelli.taxcodeapi.service.AuthenticationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

/**
 * This controller class exposes the service that create a token for a valid user
 */

@RestController
public class AuthController {
    private TokenProvider tokenProvider;
    private AuthenticationService authenticationService;

    @Autowired
    AuthController(TokenProvider tokenProvider, AuthenticationService authenticationService) {
        this.tokenProvider = tokenProvider;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/auth/token", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the JWT token"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = ErrorDTO.class),
            @ApiResponse(code = 404, message = "Not found. User or password provided are wrong", response = ErrorDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDTO.class )
    })
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

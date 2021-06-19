package com.davidechiarelli.taxcodeapi.config;

import com.davidechiarelli.taxcodeapi.security.JWTConfigurer;
import com.davidechiarelli.taxcodeapi.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * This is the configuration class for Spring Security. It defines secured or insecured resources and defines a stateless connection with the client (for REST API)
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private TokenProvider tokenProvider;

    @Value("${app.security.enabled}")
    private boolean securityEnabled;

    @Autowired
    SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/swagger-ui/**").permitAll().and()
                .authorizeRequests().antMatchers("**/*.html").permitAll().and()
                .authorizeRequests().antMatchers("/auth/**").permitAll().and()
                .authorizeRequests().antMatchers("/v3/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/**").authenticated().and()
                .apply(securityConfigurerAdapter());

        http.csrf().disable().cors().disable();

        if (securityEnabled)
            http.authorizeRequests().antMatchers("/api/**").authenticated();
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}

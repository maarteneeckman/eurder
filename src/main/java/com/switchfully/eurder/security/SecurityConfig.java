package com.switchfully.eurder.security;

import com.switchfully.eurder.security.authentication.EurderAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final EurderAuthenticationEntryPoint authEntryPoint;
    private final EurderAuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(EurderAuthenticationEntryPoint authEntryPoint, EurderAuthenticationProvider authenticationProvider) {
        this.authEntryPoint = authEntryPoint;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        CsrfConfigurer<HttpSecurity> csrf = http.csrf();
        HttpSecurity disable = csrf.disable();
        http.csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "customers").permitAll()
//                .antMatchers("/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

}

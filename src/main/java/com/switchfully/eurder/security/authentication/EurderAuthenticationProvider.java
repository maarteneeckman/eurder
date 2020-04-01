package com.switchfully.eurder.security.authentication;

import com.switchfully.eurder.security.authentication.external.ExternalAuthentication;
import com.switchfully.eurder.security.authentication.external.FakeAuthenticationService;
import com.switchfully.eurder.security.authentication.feature.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EurderAuthenticationProvider implements AuthenticationProvider {
    FakeAuthenticationService authService;

    @Autowired
    public EurderAuthenticationProvider(FakeAuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        ExternalAuthentication user = authService.getUser(username, password);
        if (user == null) {
            throw new BadCredentialsException("Username and password not found.");
        } else {
            return new UsernamePasswordAuthenticationToken(username, password, rolesToGrantedAuthorities(Feature.getFeaturesForRoles(user.getRoles())));
        }
    }

    private Collection<? extends GrantedAuthority> rolesToGrantedAuthorities(List<Feature> features) {
        return features.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

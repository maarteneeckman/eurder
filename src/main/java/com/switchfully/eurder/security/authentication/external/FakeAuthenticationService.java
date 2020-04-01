package com.switchfully.eurder.security.authentication.external;

import com.switchfully.eurder.security.authentication.feature.EurderRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeAuthenticationService {
    private final List<ExternalAuthentication> externalAuthentications;

    @Autowired
    public FakeAuthenticationService(List<ExternalAuthentication> externalAuthentications) {
        this.externalAuthentications = new ArrayList<>();
        this.externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("admin").withPassword("admin").withRoles(List.of(EurderRole.ADMIN)));
        this.externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("customer1").withPassword("customer1").withRoles(List.of(EurderRole.CUSTOMER)));
        this.externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("customer2").withPassword("customer2").withRoles(List.of(EurderRole.CUSTOMER)));
    }

    public ExternalAuthentication getUser(String username, String password) {
        return externalAuthentications.stream()
                .filter(externalAuthentication -> externalAuthentication.getUsername().equals(username))
                .filter(externalAuthentication -> externalAuthentication.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void addMember(String name, String password, List<EurderRole> roles){
        externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername(name).withPassword(password).withRoles(roles));
    }

}

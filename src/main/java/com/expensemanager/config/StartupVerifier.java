package com.expensemanager.config;

import com.expensemanager.identity.config.JwtProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class StartupVerifier {

    private final JwtProperties jwtProperties;

    public StartupVerifier(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void verify() {

        System.out.println("========== JWT CONFIG ==========");
        System.out.println("Access Secret : " + jwtProperties.getAccessSecret());
        System.out.println("Refresh Secret: " + jwtProperties.getRefreshSecret());
        System.out.println("Access Expiry : " + jwtProperties.getAccessExpiration());
        System.out.println("Refresh Expiry: " + jwtProperties.getRefreshExpiration());
        System.out.println("================================");
    }
}
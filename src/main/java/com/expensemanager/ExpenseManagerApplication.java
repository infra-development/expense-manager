package com.expensemanager;

import com.expensemanager.identity.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class ExpenseManagerApplication {

    public static void main(String[] args) {
        System.out.println("Hello World! This is the Expense Manager Application.");
        SpringApplication.run(ExpenseManagerApplication.class, args);
    }
}

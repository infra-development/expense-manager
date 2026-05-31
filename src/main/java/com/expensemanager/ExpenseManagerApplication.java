package com.expensemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseManagerApplication {

    public static void main(String[] args) {
        System.out.println("Hello World! This is the Expense Manager Application.");

        SpringApplication.run(ExpenseManagerApplication.class, args);
    }
}

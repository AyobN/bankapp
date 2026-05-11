package com.ayobn.backend.config

import com.ayobn.backend.account.Account
import com.ayobn.backend.account.AccountRepository
import com.ayobn.backend.customer.Customer
import com.ayobn.backend.customer.CustomerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class DataSeeder {

    @Bean
    fun seedDatabase(
        customerRepository: CustomerRepository,
        accountRepository: AccountRepository,
        passwordEncoder: PasswordEncoder,
    ): CommandLineRunner = CommandLineRunner {
        if (customerRepository.count() > 0) {
            println(">>> Customers already exist, skipping seed.")
            return@CommandLineRunner
        }

        val hash = passwordEncoder.encode("password123")!!

        customerRepository.saveAll(
            listOf(
                Customer(customerNumber = 100000001, passwordHash = hash),
                Customer(customerNumber = 100000002, passwordHash = hash),
                Customer(customerNumber = 100000003, passwordHash = hash),
                Customer(customerNumber = 100000004, passwordHash = hash),
                Customer(customerNumber = 100000005, passwordHash = hash),
            )
        )

        accountRepository.saveAll(
            listOf(
                Account(accountNumber = "100000001", customerNumber = 100000001, balance = 1500.50f, nickname = "Main Checking"),
                Account(accountNumber = "100000002", customerNumber = 100000001, balance = 12000.00f, nickname = "Savings"),
                Account(accountNumber = "100000003", customerNumber = 100000001, balance = 250.75f, nickname = null),

                Account(accountNumber = "200000001", customerNumber = 100000002, balance = 750.25f, nickname = "Vacation Fund"),

                Account(accountNumber = "300000001", customerNumber = 100000003, balance = 5000.00f, nickname = "Emergency Fund"),
                Account(accountNumber = "300000002", customerNumber = 100000003, balance = 100.00f, nickname = null),

                Account(accountNumber = "400000001", customerNumber = 100000004, balance = 25000.00f, nickname = "House Down Payment"),
                Account(accountNumber = "400000002", customerNumber = 100000004, balance = 800.00f, nickname = "Daily Spending"),
                Account(accountNumber = "400000003", customerNumber = 100000004, balance = 15000.00f, nickname = null),
                Account(accountNumber = "400000004", customerNumber = 100000004, balance = 3200.50f, nickname = "Tax Savings"),

                Account(accountNumber = "500000001", customerNumber = 100000005, balance = 450.00f, nickname = null),
                Account(accountNumber = "500000002", customerNumber = 100000005, balance = 8900.25f, nickname = "Investment"),
            )
        )

        println(">>> Database seeded with 5 customers and 12 accounts (password: password123)")
    }
}
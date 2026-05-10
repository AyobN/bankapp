package com.ayobn.backend.auth

import com.ayobn.backend.auth.dto.LoginRequest
import com.ayobn.backend.auth.dto.LoginResponse
import com.ayobn.backend.customer.CustomerRepository
import com.ayobn.backend.security.JwtService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@Service
class AuthService(
    private val customerRepository: CustomerRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${app.jwt.expiration-ms}") private val expirationMs: Long,
) {

    fun login(request: LoginRequest): LoginResponse {
        val customerNumber = request.customerNumber!!
        val password = request.password!!

        val customer = customerRepository.findById(customerNumber)
            .orElseThrow { ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials") }

        if (!passwordEncoder.matches(password, customer.passwordHash)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
        }

        val token = jwtService.generateToken(customerNumber)

        return LoginResponse(
            accessToken = token,
            expiresIn = expirationMs / 1000,
        )
    }
}
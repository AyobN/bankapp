package com.ayobn.backend.account

import com.ayobn.backend.account.dto.AccountResponse
import com.ayobn.backend.account.dto.UpdateNicknameRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService,
) {

    @GetMapping
    fun list(): List<AccountResponse> {
        val customerNumber = SecurityContextHolder.getContext().authentication?.principal as? Int
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated")

        return accountService.findAllForCustomer(customerNumber)
    }

    @GetMapping("/{accountNumber}")
    fun getOne(@PathVariable accountNumber: String): AccountResponse {
        val customerNumber = SecurityContextHolder.getContext().authentication?.principal as? Int
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated")

        return accountService.findOneForCustomer(accountNumber, customerNumber)
    }

    @PatchMapping("/{accountNumber}")
    fun updateNickname(
        @PathVariable accountNumber: String,
        @Valid @RequestBody request: UpdateNicknameRequest,
    ): AccountResponse {
        val customerNumber = SecurityContextHolder.getContext().authentication?.principal as? Int
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated")

        return accountService.updateNickname(accountNumber, customerNumber, request)
    }
}
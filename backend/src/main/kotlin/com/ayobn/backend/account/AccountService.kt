package com.ayobn.backend.account

import com.ayobn.backend.account.dto.AccountResponse
import com.ayobn.backend.account.dto.UpdateNicknameRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {

    fun findAllForCustomer(customerNumber: Int): List<AccountResponse> {
        return accountRepository.findAllByCustomerNumber(customerNumber)
            .map { it.toResponse() }
    }

    fun findOneForCustomer(accountNumber: String, customerNumber: Int): AccountResponse {
        val account = accountRepository.findByAccountNumberAndCustomerNumber(
            accountNumber, customerNumber,
        ) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")

        return account.toResponse()
    }

    @Transactional
    fun updateNickname(
        accountNumber: String,
        customerNumber: Int,
        request: UpdateNicknameRequest,
    ): AccountResponse {
        val account = accountRepository.findByAccountNumberAndCustomerNumber(
            accountNumber, customerNumber,
        ) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")

        account.nickname = request.nickname

        return account.toResponse()
    }

    private fun Account.toResponse() = AccountResponse(
        accountNumber = this.accountNumber,
        balance = this.balance,
        nickname = this.nickname,
    )
}
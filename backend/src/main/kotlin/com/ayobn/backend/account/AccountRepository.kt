package com.ayobn.backend.account

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, String> {

    fun findAllByCustomerNumber(customerNumber: Int): List<Account>

    // fetch accounts by customer number and account number
    // customer can only fetch accounts belonging to them
    fun findByAccountNumberAndCustomerNumber(
        accountNumber: String,
        customerNumber: Int,
    ): Account?
}
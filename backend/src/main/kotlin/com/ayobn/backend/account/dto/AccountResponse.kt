package com.ayobn.backend.account.dto

data class AccountResponse(
    val accountNumber: String,
    val balance: Float,
    val nickname: String?,
)
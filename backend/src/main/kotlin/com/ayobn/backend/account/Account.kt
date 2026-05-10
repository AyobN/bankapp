package com.ayobn.backend.account

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "accounts")
class Account(
    @Id
    @Column(name = "account_number", nullable = false, length = 9, columnDefinition = "CHAR(9)")
    val accountNumber: String,

    @Column(name = "customer_number", nullable = false)
    val customerNumber: Int,

    @Column(name = "balance", nullable = false)
    val balance: Float,

    @Column(name = "nickname", length = 255)
    var nickname: String?,
)
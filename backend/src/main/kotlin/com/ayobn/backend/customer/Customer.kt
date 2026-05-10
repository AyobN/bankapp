package com.ayobn.backend.customer

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "customers")
class Customer(
    @Id
    @Column(name = "customer_number", nullable = false)
    val customerNumber: Int,

    @Column(name = "password_hash", nullable = false, length = 255)
    var passwordHash: String,
)
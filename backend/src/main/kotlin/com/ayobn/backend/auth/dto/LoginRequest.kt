package com.ayobn.backend.auth.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class LoginRequest(
    @field:NotNull
    @field:Min(100_000_000)
    @field:Max(999_999_999)
    val customerNumber: Int?,

    @field:NotBlank
    val password: String?,
)
package com.ayobn.backend.account.dto

import jakarta.validation.constraints.Size

data class UpdateNicknameRequest(
    @field:Size(max = 255)
    val nickname: String?,
)
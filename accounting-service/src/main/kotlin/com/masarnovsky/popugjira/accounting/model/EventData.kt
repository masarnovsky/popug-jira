package com.masarnovsky.popugjira.accounting.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskCreated(
    val publicId: String,
    var title: String,
    var description: String,
    var status: Status = Status.OPEN,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
)

data class TaskAssigned(
    val taskPublicId: String,
    val accountPublicId: String,
)

data class TaskClosed(
    val taskPublicId: String,
    val accountPublicId: String,
)

data class PayoutDto(
    val accountPublicId: String,
    val funds: BigDecimal,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AccountDto(
    val publicId: String,
    val username: String,
    val email: String,
    var roles: List<Role>,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
)
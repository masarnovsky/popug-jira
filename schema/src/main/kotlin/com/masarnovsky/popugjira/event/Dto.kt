package com.masarnovsky.popugjira.event

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class AccountDto(
    val publicId: String,
    val username: String,
    val email: String,
    var roles: List<String>,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskCreatedDto(
    val publicId: String,
    var title: String,
    var description: String,
    var status: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TransactionDto(
    val accountPublicId: String,
    val taskPublicId: String? = null,
    val credit: BigDecimal = BigDecimal.ZERO,
    val debt: BigDecimal = BigDecimal.ZERO,
    val type: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

data class TaskAssignedDto(
    val taskPublicId: String,
    val accountPublicId: String,
)

data class TaskClosedDto(
    val taskPublicId: String,
    val accountPublicId: String,
)

data class PayoutDto(
    val accountPublicId: String,
    val funds: BigDecimal,
)
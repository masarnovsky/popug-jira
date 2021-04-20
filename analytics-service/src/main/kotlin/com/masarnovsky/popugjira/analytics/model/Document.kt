package com.masarnovsky.popugjira.analytics.model

import com.masarnovsky.popugjira.event.AccountDto
import com.masarnovsky.popugjira.event.TransactionDto
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document
data class Transaction(
    @Id
    val id: ObjectId = ObjectId.get(),
    val accountPublicId: String,
    val taskPublicId: String? = null,
    val credit: BigDecimal = BigDecimal.ZERO,
    val debt: BigDecimal = BigDecimal.ZERO,
    val type: TransactionType,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

@Document
data class Account(
    @Id
    val id: ObjectId = ObjectId.get(),
    val publicId: String,
    val username: String,
    val email: String,
    var roles: List<Role>,
    var walletAmount: BigDecimal = BigDecimal.ZERO,
    var calculatedAt: LocalDateTime = LocalDateTime.now(),
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
)

@Document
data class Task(
    @Id
    val id: ObjectId = ObjectId.get(),
    val publicId: String,
    var title: String,
    var description: String,
    var status: Status = Status.OPEN,
    var account: Account? = null,
    val price: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)

enum class Status {
    OPEN, ASSIGNED, CLOSED
}

enum class Role {
    EMPLOYEE, MANAGER, ADMIN, ACCOUNTANT
}

enum class TransactionType {
    DEBT, CREDIT, PAYOUT
}

fun TransactionDto.toTransaction() = Transaction(
    accountPublicId = accountPublicId,
    taskPublicId = taskPublicId,
    credit = credit,
    debt = debt,
    type = TransactionType.valueOf(type),
    createdAt = createdAt,
)

fun AccountDto.toAccount() = Account(
    publicId = publicId,
    username = username,
    email = email,
    roles = roles.map { Role.valueOf(it) },
    createdAt = createdAt,
    updatedAt = updatedAt,
)
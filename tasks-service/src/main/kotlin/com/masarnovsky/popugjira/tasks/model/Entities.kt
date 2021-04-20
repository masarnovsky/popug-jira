package com.masarnovsky.popugjira.tasks.model

import main.kotlin.com.masarnovsky.popugjira.event.AccountDto
import main.kotlin.com.masarnovsky.popugjira.event.TaskAssignedDto
import main.kotlin.com.masarnovsky.popugjira.event.TaskClosedDto
import main.kotlin.com.masarnovsky.popugjira.event.TaskCreatedDto
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document
data class Account(
    @Id
    val id: ObjectId = ObjectId.get(),
    val publicId: String,
    val username: String,
    val email: String,
    var roles: List<Role>,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
)

@Document
data class Task(
    @Id
    val id: ObjectId = ObjectId.get(),
    val publicId: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String,
    var status: Status = Status.OPEN,
    var account: Account? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)

enum class Status {
    OPEN, ASSIGNED, CLOSED
}

enum class Role {
    EMPLOYEE, MANAGER, ADMIN, ACCOUNTANT
}

fun Task.toTaskCreatedDto() = TaskCreatedDto(
    publicId = publicId,
    title = title,
    description = description,
    status = status.name,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun TaskAssigned.toTaskAssignedDto() = TaskAssignedDto(
    taskPublicId, accountPublicId
)

fun TaskClosed.toTaskClosedDto() = TaskClosedDto(
    taskPublicId, accountPublicId
)

fun AccountDto.toAccount() = Account(
    publicId = publicId,
    username = username,
    email = email,
    roles = roles.map { Role.valueOf(it) },
    createdAt = createdAt,
    updatedAt = updatedAt,
)
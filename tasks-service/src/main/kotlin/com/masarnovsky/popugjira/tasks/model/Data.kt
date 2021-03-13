package com.masarnovsky.popugjira.tasks.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Account(
        @Id
        val id: ObjectId = ObjectId.get(),
        val publicId: UUID = UUID.randomUUID(),
        val username: String,
        var password: String,
        var phoneNumber: String? = null,
        val email: String,
        var role: Role,
)

@Document
data class Task(
        @Id
        val id: ObjectId = ObjectId.get(),
        val publicId: UUID = UUID.randomUUID(),
        val title: String,
        var description: String,
        var status: Status = Status.OPEN,
        var account: Account? = null,
)

enum class Status {
    OPEN, ASSIGNED, CLOSED
}

enum class Role {
    EMPLOYEE, MANAGER, ADMIN, ACCOUNTANT
}
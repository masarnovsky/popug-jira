package com.masarnovsky.popugjira.tasks.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

class TaskDto(
    val title: String,
    var description: String,
)

data class TaskAssigned(
    val taskPublicId: String,
    val accountPublicId: String,
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
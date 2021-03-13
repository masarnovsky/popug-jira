package com.masarnovsky.popugjira.tasks.model

class TaskDto(
        val title:String,
        var description: String,
)

class AccountDto(
        val username:String,
        val password:String,
        var phoneNumber: String?,
        val email: String,
        var role: String,
)
package com.masarnovsky.popugjira.tasks.event

import com.masarnovsky.popugjira.tasks.model.AccountDto
import com.masarnovsky.popugjira.tasks.model.Task
import com.masarnovsky.popugjira.tasks.model.TaskAssigned
import com.masarnovsky.popugjira.tasks.model.TaskClosed

class TaskCreatedEvent(name: String, service: String, val task: Task) : Event("TaskCreated", service) {
    constructor(service: String, task: Task) : this("TaskCreated", service, task)
}

class TaskClosedEvent(name: String, service: String, val task: TaskClosed) : Event("TaskClosed", service) {
    constructor(service: String, task: TaskClosed) : this("TaskClosed", service, task)
}

class TaskAssignedEvent(name: String, service: String, val task: TaskAssigned) : Event("TaskAssigned", service) {
    constructor(service: String, task: TaskAssigned) : this("TaskAssigned", service, task)
}

class AccountCreatedEvent(name: String, service: String, val account: AccountDto) : Event("AccountCreated", service)

open class Event(val name: String, val service: String)
package com.masarnovsky.popugjira.accounting.event

import com.masarnovsky.popugjira.accounting.model.AccountDto
import com.masarnovsky.popugjira.accounting.model.TaskCreated
import com.masarnovsky.popugjira.accounting.model.TaskAssigned
import com.masarnovsky.popugjira.accounting.model.TaskClosed

class TaskCreatedEvent(name: String, service: String, val taskCreated: TaskCreated) : Event("TaskCreated", service) {
    constructor(service: String, taskCreated: TaskCreated) : this("TaskCreated", service, taskCreated)
}

class TaskClosedEvent(name: String, service: String, val task: TaskClosed) : Event("TaskClosed", service) {
    constructor(service: String, task: TaskClosed) : this("TaskClosed", service, task)
}

class TaskAssignedEvent(name: String, service: String, val task: TaskAssigned) : Event("TaskAssigned", service) {
    constructor(service: String, taskAssigned: TaskAssigned) : this("TaskAssigned", service, taskAssigned)
}

class AccountCreatedEvent(name: String, service: String, val account: AccountDto) : Event("AccountCreated", service)


open class Event(val name: String, val service: String)
package com.masarnovsky.popugjira.tasks.event

import com.masarnovsky.popugjira.tasks.model.AccountDto
import com.masarnovsky.popugjira.tasks.model.Task
import com.masarnovsky.popugjira.tasks.model.TaskAssigned
import com.masarnovsky.popugjira.tasks.model.TaskClosed

class TaskCreatedEvent(name: String, val task: Task) : Event("TaskCreated") {
    constructor(task: Task) : this("TaskCreated", task)
}

class TaskClosedEvent(name: String, val task: TaskClosed) : Event("TaskClosed") {
    constructor(task: TaskClosed) : this("TaskClosed", task)
}

class TaskAssignedEvent(name: String, val task: TaskAssigned) : Event("TaskAssigned") {
    constructor(taskAssigned: TaskAssigned) : this("TaskAssigned", taskAssigned)
}

class AccountCreatedEvent(name: String, val account: AccountDto): Event("AccountCreated")

open class Event(val name: String)
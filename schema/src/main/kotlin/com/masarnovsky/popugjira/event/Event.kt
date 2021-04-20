package com.masarnovsky.popugjira.event

open class Event(val name: String, val service: String)

class AccountCreatedEvent(name: String, service: String, val account: AccountDto) : Event("AccountCreated", service)

class TaskCreatedEvent(name: String, service: String, val task: TaskCreatedDto) : Event("TaskCreated", service) {
    constructor(service: String, task: TaskCreatedDto) : this("TaskCreated", service, task)
}

class TaskClosedEvent(name: String, service: String, val task: TaskClosedDto) : Event("TaskClosed", service) {
    constructor(service: String, task: TaskClosedDto) : this("TaskClosed", service, task)
}

class TaskAssignedEvent(name: String, service: String, val task: TaskAssignedDto) : Event("TaskAssigned", service) {
    constructor(service: String, task: TaskAssignedDto) : this("TaskAssigned", service, task)
}

class PayoutCreatedEvent(name: String, service: String, val payout: PayoutDto) : Event("PayoutCreated", service) {
    constructor(service: String, payout: PayoutDto) : this("PayoutCreated", service, payout)
}

class TransactionCreatedEvent(name: String, service: String, val transaction: TransactionDto) :
    Event("TransactionCreated", service) {
    constructor(service: String, transaction: TransactionDto) : this("TransactionCreated", service, transaction)
}
package com.masarnovsky.popugjira.accounting.event

import com.masarnovsky.popugjira.accounting.model.*

class TaskCreatedEvent(name: String, service: String, val task: TaskCreated) : Event("TaskCreated", service) {
    constructor(service: String, task: TaskCreated) : this("TaskCreated", service, task)
}

class TaskClosedEvent(name: String, service: String, val task: TaskClosed) : Event("TaskClosed", service) {
    constructor(service: String, task: TaskClosed) : this("TaskClosed", service, task)
}

class TaskAssignedEvent(name: String, service: String, val task: TaskAssigned) : Event("TaskAssigned", service) {
    constructor(service: String, task: TaskAssigned) : this("TaskAssigned", service, task)
}

class AccountCreatedEvent(name: String, service: String, val account: AccountDto) : Event("AccountCreated", service)

class PayoutCreated(name: String, service: String, val payout: PayoutDto) : Event("PayoutCreated", service) {
    constructor(service: String, payout: PayoutDto) : this("PayoutCreated", service, payout)
}

class TransactionCreated(name: String, service: String, val transaction: Transaction) :
    Event("TransactionCreated", service) {
    constructor(service: String, transaction: Transaction) : this("TransactionCreated", service, transaction)
}

open class Event(val name: String, val service: String)
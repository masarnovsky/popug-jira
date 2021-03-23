package com.masarnovsky.popugjira.tasks.event

import com.masarnovsky.popugjira.tasks.model.Task
import com.masarnovsky.popugjira.tasks.model.TaskAssigned

class TaskCreatedEvent(private val task: Task) : Event("TaskCreated")

class TaskClosedEvent(private val task: Task) : Event("TaskClosed")

class TaskAssignedEvent(private val taskAssigned: TaskAssigned) : Event("TaskAssigned")

open class Event(val name: String)
package com.masarnovsky.popugjira.tasks.service

import com.masarnovsky.popugjira.tasks.TASKS_STREAM_TOPIC
import com.masarnovsky.popugjira.tasks.TASK_ASSIGNED_TOPIC
import com.masarnovsky.popugjira.tasks.TASK_CLOSED_TOPIC
import com.masarnovsky.popugjira.tasks.TASK_CREATED_TOPIC
import com.masarnovsky.popugjira.tasks.event.Event
import com.masarnovsky.popugjira.tasks.event.TaskAssignedEvent
import com.masarnovsky.popugjira.tasks.event.TaskClosedEvent
import com.masarnovsky.popugjira.tasks.event.TaskCreatedEvent
import com.masarnovsky.popugjira.tasks.model.*
import com.masarnovsky.popugjira.tasks.repository.TaskRepository
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

private val LOGGER = KotlinLogging.logger {}

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val accountService: AccountService,
    private val kafkaTemplate: KafkaTemplate<String, Event>,
) {

    fun findAll(): List<Task> {
        return taskRepository.findAllByStatusIn(listOf(Status.OPEN, Status.ASSIGNED, Status.CLOSED))
    }

    fun findById(id: String): Task {
        return taskRepository.findById(ObjectId(id))
    }

    fun findByPublicId(id: String): Task {
        return taskRepository.findByPublicId(id)
    }

    @Transactional
    fun createTask(dto: TaskDto): Task {
        val newTask = taskRepository.save(Task(title = dto.title, description = dto.description))

        val event = TaskCreatedEvent(task = newTask)
        kafkaTemplate.send(TASKS_STREAM_TOPIC, event)
        kafkaTemplate.send(TASK_CREATED_TOPIC, event)
        LOGGER.info { "${event.name} was produced => ${event.task}" }

        return newTask
    }

    @Transactional
    fun assignTasks(): List<Task> {
        val accounts = accountService.findAll()
        val tasksForAssignment = taskRepository.findAllByStatusIn(listOf(Status.OPEN, Status.ASSIGNED))

        tasksForAssignment.forEach {
            it.account = accounts.random()
            it.status = Status.ASSIGNED

            val event = TaskAssignedEvent(TaskAssigned(it.publicId, it.account!!.publicId))
            kafkaTemplate.send(TASK_ASSIGNED_TOPIC, event)
            LOGGER.info { "${event.name} was produced => ${event.task}" }
        }

        return taskRepository.saveAll(tasksForAssignment)
    }

    @Transactional
    fun closeTask(id: String): Task {
        val task = taskRepository.findByPublicId(id)
        task.status = Status.CLOSED
        val updatedTask = taskRepository.save(task)

        val event = TaskClosedEvent(TaskClosed(updatedTask.publicId, task.account!!.publicId))
        kafkaTemplate.send(TASK_CLOSED_TOPIC, event)
        LOGGER.info { "${event.name} was produced => ${event.task}" }

        return updatedTask
    }
}
package com.masarnovsky.popugjira.tasks.service

import com.masarnovsky.popugjira.tasks.model.Status
import com.masarnovsky.popugjira.tasks.model.Task
import com.masarnovsky.popugjira.tasks.model.TaskDto
import com.masarnovsky.popugjira.tasks.repository.TaskRepository
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

private val LOGGER = KotlinLogging.logger {}
private val TASKS_TOPIC = "tasks"
private val TASKS_STREAM_TOPIC = "tasks-stream"

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val accountService: AccountService,
    private val kafkaTemplate: KafkaTemplate<String, Task>,
) {

    fun findAll(): List<Task> {
        return taskRepository.findAllByStatusIn(listOf(Status.OPEN, Status.ASSIGNED, Status.CLOSED))
    }

    fun findById(id: String): Task {
        return taskRepository.findById(ObjectId(id))
    }

    fun findByPublicId(id: String): Task {
        return taskRepository.findByPublicId(UUID.fromString(id))
    }

    @Transactional
    fun createTask(dto: TaskDto): Task {
        val newTask = taskRepository.save(Task(title = dto.title, description = dto.description))

        LOGGER.info { "new task was produced => $newTask" }
        kafkaTemplate.send(TASKS_STREAM_TOPIC, newTask)

        return newTask
    }

    @Transactional
    fun assignTasks(): List<Task> {
        val accounts = accountService.findAll()
        val tasksForAssignment = taskRepository.findAllByStatusIn(listOf(Status.OPEN, Status.ASSIGNED))

        tasksForAssignment.forEach {
            it.account = accounts.random()
            it.status = Status.ASSIGNED

//            kafkaTemplate.send(TASKS_TOPIC, TaskAssignedEvent(TaskAssigned(it.publicId, it.account.publicId)))
            LOGGER.info { "Task ${it.publicId} was assigned to ${it.account!!.publicId}" }
        }

        return taskRepository.saveAll(tasksForAssignment)
    }

    @Transactional
    fun closeTask(id: String): Task {
        val task = taskRepository.findById(ObjectId(id))
        task.status = Status.CLOSED
        val updatedTask = taskRepository.save(task)

//        kafkaTemplate.send(TASKS_TOPIC, TaskClosedEvent(task))
        LOGGER.info { "Task ${task.publicId} was closed" }

        return updatedTask
    }

    fun clear() {
        taskRepository.deleteAll()
    }
}
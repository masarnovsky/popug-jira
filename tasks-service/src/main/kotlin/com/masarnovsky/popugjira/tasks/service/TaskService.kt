package com.masarnovsky.popugjira.tasks.service

import com.masarnovsky.popugjira.tasks.model.Status
import com.masarnovsky.popugjira.tasks.model.Task
import com.masarnovsky.popugjira.tasks.model.TaskDto
import com.masarnovsky.popugjira.tasks.repository.TaskRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService(
        private val taskRepository: TaskRepository,
        private val accountService: AccountService,
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

    fun createTask(dto: TaskDto): Task {
        return taskRepository.save(Task(title = dto.title, description = dto.description))
    }

    fun assignTasks(): List<Task> {
        val accounts = accountService.findAll()
        val tasksForAssignment = taskRepository.findAllByStatusIn(listOf(Status.OPEN, Status.ASSIGNED))

        tasksForAssignment.forEach {
            it.account = accounts.random()
            it.status = Status.ASSIGNED
        }

        return taskRepository.saveAll(tasksForAssignment)
    }

    fun closeTask(id: String): Task {
        val task = taskRepository.findById(ObjectId(id))
        task.status = Status.CLOSED
        return taskRepository.save(task)
    }

    fun clear() {
        taskRepository.deleteAll()
    }
}
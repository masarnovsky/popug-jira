package com.masarnovsky.popugjira.accounting.service

import com.masarnovsky.popugjira.accounting.NEW_TASK_MAX_PRICE
import com.masarnovsky.popugjira.accounting.NEW_TASK_MIN_PRICE
import com.masarnovsky.popugjira.accounting.model.Task
import com.masarnovsky.popugjira.accounting.model.TaskCreated
import com.masarnovsky.popugjira.accounting.repository.TaskRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.random.Random

@Service
class TasksService(
    val taskRepository: TaskRepository,
) {

    fun save(task: Task): Task {
        return taskRepository.save(task)
    }

    fun setPriceAndSave(taskCreated: TaskCreated): Task {
        val price = BigDecimal(Random.nextInt(NEW_TASK_MIN_PRICE, NEW_TASK_MAX_PRICE))
        // send to kafka setPriceEvent (CUD)
        val task = Task(
            publicId = taskCreated.publicId, title = taskCreated.title, price = price,
            description = taskCreated.description, createdAt = taskCreated.createdAt, updatedAt = taskCreated.updatedAt
        )
        return save(task)
    }

    fun findByPublicId(publicId: String): Task {
        return taskRepository.findByPublicId(publicId)
    }
}
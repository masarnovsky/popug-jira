package com.masarnovsky.popugjira.accounting.service

import com.masarnovsky.popugjira.accounting.NEW_TASK_MAX_PRICE
import com.masarnovsky.popugjira.accounting.NEW_TASK_MIN_PRICE
import com.masarnovsky.popugjira.accounting.model.Task
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

    fun setPriceAndSave(task: Task): Task {
        val price = BigDecimal(Random.nextInt(NEW_TASK_MIN_PRICE, NEW_TASK_MAX_PRICE))
        task.price = price
        return save(task)
    }

    fun findByPublicId(publicId: String): Task {
        return taskRepository.findByPublicId(publicId)
    }
}
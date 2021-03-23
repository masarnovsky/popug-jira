package com.masarnovsky.popugjira.tasks.controller

import com.masarnovsky.popugjira.tasks.model.Task
import com.masarnovsky.popugjira.tasks.model.TaskDto
import com.masarnovsky.popugjira.tasks.service.TaskService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(
        private val taskService: TaskService
) {

    @GetMapping
    fun findAll(): List<Task> {
        return taskService.findAll()
    }

    @GetMapping("/{publicId}")
    fun findById(@PathVariable("publicId") publicId: String): Task {
        return taskService.findByPublicId(publicId)
    }

    @PostMapping
    fun createTask(@RequestBody request: TaskDto): Task {
        return taskService.createTask(request)
    }

    @PostMapping("/assign")
    fun assignTasks(): List<Task> {
        return taskService.assignTasks()
    }

    @DeleteMapping
    fun clear() {
        taskService.clear()
    }
}
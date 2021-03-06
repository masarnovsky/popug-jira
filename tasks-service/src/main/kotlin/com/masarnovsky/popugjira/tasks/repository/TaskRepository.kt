package com.masarnovsky.popugjira.tasks.repository

import com.masarnovsky.popugjira.tasks.model.Status
import com.masarnovsky.popugjira.tasks.model.Task
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TaskRepository : MongoRepository<Task, String> {
    fun findById(id: ObjectId): Task

    fun findByPublicId(id: String): Task

    fun findAllByStatusIn(statuses: List<Status>): List<Task>

    fun findAllByAccount_PublicId(accountPublicId: String): List<Task>
}
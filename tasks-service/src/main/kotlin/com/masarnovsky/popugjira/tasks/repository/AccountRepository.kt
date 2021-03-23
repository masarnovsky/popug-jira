package com.masarnovsky.popugjira.tasks.repository

import com.masarnovsky.popugjira.tasks.model.Account
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface AccountRepository : MongoRepository<Account, String> {
    override fun findAll(): List<Account>

    fun findByPublicId(publicId: String): Account
}
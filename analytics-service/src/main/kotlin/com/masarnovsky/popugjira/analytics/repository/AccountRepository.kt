package com.masarnovsky.popugjira.analytics.repository

import com.masarnovsky.popugjira.analytics.model.Account
import org.springframework.data.mongodb.repository.MongoRepository

interface AccountRepository : MongoRepository<Account, String> {
    override fun findAll(): List<Account>

    fun findByPublicId(publicId: String): Account
}
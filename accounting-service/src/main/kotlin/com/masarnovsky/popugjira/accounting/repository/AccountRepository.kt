package com.masarnovsky.popugjira.accounting.repository

import com.masarnovsky.popugjira.accounting.model.Account
import org.springframework.data.mongodb.repository.MongoRepository

interface AccountRepository : MongoRepository<Account, String> {
    override fun findAll(): List<Account>

    fun findByPublicId(publicId: String): Account
}
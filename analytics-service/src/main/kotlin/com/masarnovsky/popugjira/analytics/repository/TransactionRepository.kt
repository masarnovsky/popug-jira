package com.masarnovsky.popugjira.analytics.repository

import com.masarnovsky.popugjira.analytics.model.Transaction
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface TransactionRepository : MongoRepository<Transaction, String> {
    fun findTaskByCreatedAtBetween(from: LocalDateTime, to: LocalDateTime): List<Transaction>
}
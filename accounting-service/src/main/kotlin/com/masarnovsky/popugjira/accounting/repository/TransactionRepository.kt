package com.masarnovsky.popugjira.accounting.repository

import com.masarnovsky.popugjira.accounting.model.Transaction
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface TransactionRepository : MongoRepository<Transaction, String> {
    fun findTaskByCreatedAtBetween(from: LocalDateTime, to: LocalDateTime): List<Transaction>
}
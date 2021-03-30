package com.masarnovsky.popugjira.accounting.repository

import com.masarnovsky.popugjira.accounting.model.Transaction
import org.springframework.data.mongodb.repository.MongoRepository

interface TransactionRepository : MongoRepository<Transaction, String>
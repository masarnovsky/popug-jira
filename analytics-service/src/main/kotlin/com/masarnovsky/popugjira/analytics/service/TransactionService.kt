package com.masarnovsky.popugjira.analytics.service

import com.masarnovsky.popugjira.analytics.model.Transaction
import com.masarnovsky.popugjira.analytics.model.TransactionDto
import com.masarnovsky.popugjira.analytics.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

@Service
class TransactionService(
    private val repository: TransactionRepository,
) {
    fun save(transaction: TransactionDto): Transaction {
        return repository.save(
            Transaction(
                accountPublicId = transaction.accountPublicId,
                taskPublicId = transaction.taskPublicId,
                credit = transaction.credit,
                debt = transaction.debt,
                type = transaction.type,
                createdAt = transaction.createdAt
            )
        )
    }

    fun calculateEarnedByManagers(): BigDecimal {
        val from = LocalDate.now().atTime(0, 0)
        val to = LocalDate.now().atTime(23, 59)

        val earned: BigDecimal = BigDecimal.ZERO
        repository.findTaskByCreatedAtBetween(from, to).forEach {
            earned.plus(it.debt)
            earned.minus(it.credit)
        }

        return earned.multiply(BigDecimal(-1))
    }

    fun calculateDebtorsForToday(): Int {
        val from = LocalDate.now().atTime(0, 0)
        val to = LocalDate.now().atTime(23, 59)

        return repository.findTaskByCreatedAtBetween(from, to)
            .groupBy { it.accountPublicId }
            .map { (_, value) ->
                val earned: BigDecimal = BigDecimal.ZERO
                value.forEach {
                    earned.plus(it.debt)
                    earned.minus(it.credit)
                }
                earned
            }
            .filter { it < BigDecimal.ZERO }
            .count()
    }

    fun mostExpensiveTask(period: String): BigDecimal {
        var from = LocalDate.now().atTime(0, 0)
        var to = LocalDate.now().atTime(23, 59)

        when (period) {
            "week" -> {
                from = LocalDate.now().minusDays(7).atTime(0, 0)
                to = LocalDate.now().atTime(23, 59)
            }
            "month" -> {
                from = LocalDate.now().minusDays(31).atTime(0, 0)
                to = LocalDate.now().atTime(23, 59)
            }
        }

        return repository.findTaskByCreatedAtBetween(from, to)
            .sortedByDescending { it.debt }
            .first()
            .debt
    }
}
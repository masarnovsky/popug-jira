package com.masarnovsky.popugjira.accounting.service

import com.masarnovsky.popugjira.accounting.CLOSED_TASK_COEFFICIENT
import com.masarnovsky.popugjira.accounting.model.TaskAssigned
import com.masarnovsky.popugjira.accounting.model.TaskClosed
import com.masarnovsky.popugjira.accounting.model.Transaction
import com.masarnovsky.popugjira.accounting.model.TransactionType
import com.masarnovsky.popugjira.accounting.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class TransactionService(
    val transactionRepository: TransactionRepository,
    val tasksService: TasksService,
    val accountService: AccountService,
) {

    @Transactional
    fun creditFunds(taskAssigned: TaskAssigned): Transaction {
        val task = tasksService.findByPublicId(taskAssigned.taskPublicId)
        val account = accountService.findByPublicId(taskAssigned.accountPublicId)

        account.walletAmount -= task.price
        accountService.save(account)

        return transactionRepository.save(
            Transaction(
                accountPublicId = account.publicId,
                taskPublicId = task.publicId,
                credit = task.price,
                type = TransactionType.CREDIT,
            )
        )
    }

    @Transactional
    fun debtFunds(taskClosed: TaskClosed): Transaction {
        val task = tasksService.findByPublicId(taskClosed.taskPublicId)
        val account = accountService.findByPublicId(taskClosed.accountPublicId)

        account.walletAmount += task.price * BigDecimal(CLOSED_TASK_COEFFICIENT)
        accountService.save(account)

        return transactionRepository.save(
            Transaction(
                accountPublicId = account.publicId,
                taskPublicId = task.publicId,
                debt = task.price,
                type = TransactionType.DEBT,
            )
        )
    }

    @Transactional
    fun payout(accountPublicId: String): Boolean {
        val account = accountService.findByPublicId(accountPublicId)

        return if (account.walletAmount > BigDecimal.ZERO) {
            transactionRepository.save(
                Transaction(
                    accountPublicId = account.publicId,
                    credit = account.walletAmount,
                    type = TransactionType.PAYOUT,
                )
            )

            account.walletAmount = BigDecimal.ZERO
            accountService.save(account)

            true
        } else {
            false
        }
    }
}
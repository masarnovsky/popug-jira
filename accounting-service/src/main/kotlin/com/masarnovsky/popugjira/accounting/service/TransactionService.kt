package com.masarnovsky.popugjira.accounting.service

import com.masarnovsky.popugjira.accounting.CLOSED_TASK_COEFFICIENT
import com.masarnovsky.popugjira.accounting.PAYOUT_CREATED
import com.masarnovsky.popugjira.accounting.SERVICE_NAME
import com.masarnovsky.popugjira.accounting.TRANSACTION_CREATED
import com.masarnovsky.popugjira.accounting.model.Transaction
import com.masarnovsky.popugjira.accounting.model.TransactionType
import com.masarnovsky.popugjira.accounting.model.toTransactionDto
import com.masarnovsky.popugjira.accounting.repository.TransactionRepository
import com.masarnovsky.popugjira.event.*
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

private val LOGGER = KotlinLogging.logger {}

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val tasksService: TasksService,
    private val accountService: AccountService,
    private val kafkaTemplate: KafkaTemplate<String, Event>,
) {

    @Transactional
    fun creditFunds(taskAssigned: TaskAssignedDto): Transaction {
        val task = tasksService.findByPublicId(taskAssigned.taskPublicId)
        val account = accountService.findByPublicId(taskAssigned.accountPublicId)

        account.walletAmount -= task.price
        accountService.save(account)

        val transaction = transactionRepository.save(
            Transaction(
                accountPublicId = account.publicId,
                taskPublicId = task.publicId,
                credit = task.price,
                type = TransactionType.CREDIT,
            )
        )

        val event = TransactionCreatedEvent(SERVICE_NAME, transaction.toTransactionDto())
        kafkaTemplate.send(TRANSACTION_CREATED, event)
        LOGGER.info { "${event.name} was produced in ${event.service} => ${event.transaction}" }

        return transaction
    }

    @Transactional
    fun debtFunds(taskClosed: TaskClosedDto): Transaction {
        val task = tasksService.findByPublicId(taskClosed.taskPublicId)
        val account = accountService.findByPublicId(taskClosed.accountPublicId)

        val closedTaskPrice = task.price * BigDecimal(CLOSED_TASK_COEFFICIENT)
        account.walletAmount += closedTaskPrice
        accountService.save(account)

        val transaction = transactionRepository.save(
            Transaction(
                accountPublicId = account.publicId,
                taskPublicId = task.publicId,
                debt = closedTaskPrice,
                type = TransactionType.DEBT,
            )
        )

        val event = TransactionCreatedEvent(SERVICE_NAME, transaction.toTransactionDto())
        kafkaTemplate.send(TRANSACTION_CREATED, event)
        LOGGER.info { "${event.name} was produced in ${event.service} => ${event.transaction}" }

        return transaction
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

            val event = PayoutCreatedEvent(SERVICE_NAME, PayoutDto(account.publicId, account.walletAmount))
            kafkaTemplate.send(PAYOUT_CREATED, event)
            account.walletAmount = BigDecimal.ZERO
            accountService.save(account)

            true
        } else {
            false
        }
    }

    fun calculateEarnedByManagers(): BigDecimal {
        val from = LocalDate.now().atTime(0, 0)
        val to = LocalDate.now().atTime(23, 59)

        val earned: BigDecimal = BigDecimal.ZERO
        transactionRepository.findTaskByCreatedAtBetween(from, to).forEach {
            earned.plus(it.debt)
            earned.minus(it.credit)
        }

        return earned.multiply(BigDecimal(-1))
    }
}
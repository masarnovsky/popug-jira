package com.masarnovsky.popugjira.accounting.listener

import com.masarnovsky.popugjira.accounting.*
import com.masarnovsky.popugjira.accounting.model.toAccount
import com.masarnovsky.popugjira.accounting.model.toTask
import com.masarnovsky.popugjira.accounting.service.AccountService
import com.masarnovsky.popugjira.accounting.service.NotificationService
import com.masarnovsky.popugjira.accounting.service.TasksService
import com.masarnovsky.popugjira.accounting.service.TransactionService
import com.masarnovsky.popugjira.event.*
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


private val LOGGER = KotlinLogging.logger {}

@Service
class KafkaListener(
    val accountService: AccountService,
    val tasksService: TasksService,
    val transactionService: TransactionService,
    val notificationService: NotificationService,
) {

    @KafkaListener(
        topics = [ACCOUNTS_STREAM_TOPIC],
        containerFactory = "kafkaAccountCreatedEventListenerContainerFactory"
    )
    fun listenAccountsStreamTopic(event: AccountCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.account}" }
        accountService.save(event.account.toAccount())
    }

    @KafkaListener(
        topics = [TASKS_STREAM_TOPIC],
        containerFactory = "kafkaTaskCreatedEventListenerContainerFactory"
    )
    fun listenTaskCreatedTopic(event: TaskCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.task}" }
        tasksService.setPriceAndSave(event.task.toTask())
    }

    @KafkaListener(
        topics = [TASK_ASSIGNED_TOPIC],
        containerFactory = "kafkaTaskAssignedEventListenerContainerFactory"
    )
    fun listenTaskAssignedTopic(event: TaskAssignedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed ${event.service} with data: ${event.task}" }
        transactionService.creditFunds(event.task)
    }

    @KafkaListener(
        topics = [TASK_CLOSED_TOPIC],
        containerFactory = "kafkaTaskClosedEventListenerContainerFactory"
    )
    fun listenTaskClosedTopic(event: TaskClosedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.task}" }
        transactionService.debtFunds(event.task)
    }

    @KafkaListener(
        topics = [PAYOUT_CREATED],
        containerFactory = "kafkaPayoutCreatedEventListenerContainerFactory"
    )
    fun listenPayoutCreatedTopic(event: PayoutCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.payout}" }
        notificationService.sendNotification(event.payout.accountPublicId, "Hi, we sent you ${event.payout.funds}$!")
    }
}
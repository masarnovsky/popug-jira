package com.masarnovsky.popugjira.accounting.listener

import com.masarnovsky.popugjira.accounting.*
import com.masarnovsky.popugjira.accounting.event.AccountCreatedEvent
import com.masarnovsky.popugjira.accounting.event.TaskAssignedEvent
import com.masarnovsky.popugjira.accounting.event.TaskClosedEvent
import com.masarnovsky.popugjira.accounting.event.TaskCreatedEvent
import com.masarnovsky.popugjira.accounting.service.AccountService
import com.masarnovsky.popugjira.accounting.service.TransactionService
import com.masarnovsky.popugjira.accounting.service.NotificationService
import com.masarnovsky.popugjira.accounting.service.TasksService
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
        groupId = ACCOUNTS_GROUP_ID,
        containerFactory = "kafkaAccountCreatedEventListenerContainerFactory"
    )
    fun listenAccountsStreamTopic(event: AccountCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.account}" }
        accountService.save(event.account)
    }

    @KafkaListener(
        topics = [TASK_ASSIGNED_TOPIC],
        groupId = TASKS_GROUP_ID,
        containerFactory = "kafkaTaskAssignedEventListenerContainerFactory"
    )
    fun listenTaskAssignedTopic(event: TaskAssignedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed ${event.service} with data: ${event.task}" }
        transactionService.creditFunds(event.task)
        notificationService.sendNotification(
            event.task.accountPublicId,
            "task ${event.task.taskPublicId} was assigned on you"
        )
    }

    @KafkaListener(
        topics = [TASK_CLOSED_TOPIC],
        groupId = TASKS_GROUP_ID,
        containerFactory = "kafkaTaskClosedEventListenerContainerFactory"
    )
    fun listenTaskClosedTopic(event: TaskClosedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.task}" }
        transactionService.debtFunds(event.task)
        notificationService.sendNotification(
            event.task.accountPublicId,
            "task ${event.task.taskPublicId} was closed by you"
        )
    }

    @KafkaListener(
        topics = [TASK_CREATED_TOPIC],
        groupId = TASKS_GROUP_ID,
        containerFactory = "kafkaTaskCreatedEventListenerContainerFactory"
    )
    fun listenTaskCreatedTopic(event: TaskCreatedEvent) {
        tasksService.setPriceAndSave(event.taskCreated)
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.taskCreated}" }
    }
}
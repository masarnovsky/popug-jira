package com.masarnovsky.popugjira.tasks.listener

import com.masarnovsky.popugjira.tasks.*
import com.masarnovsky.popugjira.tasks.event.AccountCreatedEvent
import com.masarnovsky.popugjira.tasks.event.TaskAssignedEvent
import com.masarnovsky.popugjira.tasks.event.TaskClosedEvent
import com.masarnovsky.popugjira.tasks.event.TaskCreatedEvent
import com.masarnovsky.popugjira.tasks.service.AccountService
import com.masarnovsky.popugjira.tasks.service.NotificationService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private val LOGGER = KotlinLogging.logger {}

@Service
class KafkaListener(
    val accountService: AccountService,
    val notificationService: NotificationService,
) {

    @KafkaListener(
        topics = [ACCOUNTS_STREAM_TOPIC],
        groupId = ACCOUNTS_GROUP_ID,
        containerFactory = "kafkaAccountCreatedEventListenerContainerFactory"
    )
    fun listenAccountsStreamTopic(event: AccountCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed with data: ${event.account}" }
        accountService.save(event.account)
    }

    @KafkaListener(
        topics = [TASK_ASSIGNED_TOPIC],
        groupId = TASKS_GROUP_ID,
        containerFactory = "kafkaTaskAssignedEventListenerContainerFactory"
    )
    fun listenTaskAssignedTopic(event: TaskAssignedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed with data: ${event.task}" }
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
        LOGGER.info { "=> event ${event.name} was consumed with data: ${event.task}" }
        notificationService.sendNotification(
            event.task.accountPublicId,
            "task ${event.task.taskPublicId} was closed by you"
        )
    }

    @KafkaListener(
        topics = [TASK_CREATED_TOPIC],
        groupId = TASKS_GROUP_ID,
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun listenTaskCreatedTopic(event: TaskCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed with data: ${event.task}" }
    }
}
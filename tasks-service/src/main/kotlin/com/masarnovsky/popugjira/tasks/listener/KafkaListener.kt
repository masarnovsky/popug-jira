package com.masarnovsky.popugjira.tasks.listener

import com.masarnovsky.popugjira.tasks.ACCOUNTS_STREAM_TOPIC
import com.masarnovsky.popugjira.tasks.TASK_ASSIGNED_TOPIC
import com.masarnovsky.popugjira.tasks.event.AccountCreatedEvent
import com.masarnovsky.popugjira.tasks.event.TaskAssignedEvent
import com.masarnovsky.popugjira.tasks.service.AccountService
import com.masarnovsky.popugjira.tasks.service.NotificationService
import com.masarnovsky.popugjira.tasks.service.TaskService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private val LOGGER = KotlinLogging.logger {}

@Service
class KafkaListener(
    val accountService: AccountService,
    val taskService: TaskService,
    val notificationService: NotificationService,
) {

    @KafkaListener(
        topics = [ACCOUNTS_STREAM_TOPIC],
        containerFactory = "kafkaAccountCreatedEventListenerContainerFactory"
    )
    fun listenAccountsStreamTopic(event: AccountCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.account}" }
        accountService.save(event.account)
    }

    @KafkaListener(
        topics = [TASK_ASSIGNED_TOPIC],
        containerFactory = "kafkaTaskAssignedEventListenerContainerFactory"
    )
    fun listenTaskAssignedTopic(event: TaskAssignedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.task}" }
        val task = taskService.findByPublicId(event.task.taskPublicId)
        notificationService.sendNotification(event.task.accountPublicId, "Task ${task.title} was assigned to you")
    }
}
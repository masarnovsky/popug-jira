package com.masarnovsky.popugjira.tasks.listener

import com.masarnovsky.popugjira.tasks.event.AccountCreatedEvent
import com.masarnovsky.popugjira.tasks.service.AccountService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private val LOGGER = KotlinLogging.logger {}

@Service
class KafkaListener(
    val accountService: AccountService,
) {

    @KafkaListener(topics = ["accounts-stream"], groupId = "accounts", containerFactory = "kafkaAccountCreatedEventListenerContainerFactory")
    fun listenAccountsStream(event: AccountCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed with data: ${event.account}" }
        accountService.save(event.account)
    }

//    test listeners for tasks
}
package com.masarnovsky.popugjira.tasks.listener

import com.masarnovsky.popugjira.tasks.ACCOUNTS_STREAM_TOPIC
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

    @KafkaListener(
        topics = [ACCOUNTS_STREAM_TOPIC],
        containerFactory = "kafkaAccountCreatedEventListenerContainerFactory"
    )
    fun listenAccountsStreamTopic(event: AccountCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.account}" }
        accountService.save(event.account)
    }
}
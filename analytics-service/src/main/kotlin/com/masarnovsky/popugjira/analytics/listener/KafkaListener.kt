package com.masarnovsky.popugjira.analytics.listener

import com.masarnovsky.popugjira.analytics.ACCOUNTS_STREAM_TOPIC
import com.masarnovsky.popugjira.analytics.TRANSACTION_CREATED
import com.masarnovsky.popugjira.analytics.event.AccountCreatedEvent
import com.masarnovsky.popugjira.analytics.event.TransactionCreatedEvent
import com.masarnovsky.popugjira.analytics.service.AccountService
import com.masarnovsky.popugjira.analytics.service.TransactionService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


private val LOGGER = KotlinLogging.logger {}

@Service
class KafkaListener(
    val accountService: AccountService,
    val transactionService: TransactionService,
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
        topics = [TRANSACTION_CREATED],
        containerFactory = "transactionCreatedConsumerFactory"
    )
    fun listenTransactionCreatedTopic(event: TransactionCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.transaction}" }
        transactionService.save(event.transaction)
    }
}
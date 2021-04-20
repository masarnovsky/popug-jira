package com.masarnovsky.popugjira.analytics.listener

import com.masarnovsky.popugjira.analytics.ACCOUNTS_STREAM_TOPIC
import com.masarnovsky.popugjira.analytics.TRANSACTION_CREATED
import com.masarnovsky.popugjira.analytics.model.toAccount
import com.masarnovsky.popugjira.analytics.model.toTransaction
import com.masarnovsky.popugjira.analytics.service.AccountService
import com.masarnovsky.popugjira.analytics.service.TransactionService
import com.masarnovsky.popugjira.event.AccountCreatedEvent
import com.masarnovsky.popugjira.event.TransactionCreatedEvent
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
        accountService.save(event.account.toAccount())
    }

    @KafkaListener(
        topics = [TRANSACTION_CREATED],
        containerFactory = "kafkaTransactionCreatedEventListenerContainerFactory"
    )
    fun listenTransactionCreatedTopic(event: TransactionCreatedEvent) {
        LOGGER.info { "=> event ${event.name} was consumed from ${event.service} with data: ${event.transaction}" }
        transactionService.save(event.transaction.toTransaction())
    }
}
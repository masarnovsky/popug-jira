package com.masarnovsky.popugjira.tasks.listener

import com.masarnovsky.popugjira.tasks.model.AccountDto
import com.masarnovsky.popugjira.tasks.service.AccountService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private val LOGGER = KotlinLogging.logger {}

@Service
class KafkaListener(
    val accountService: AccountService,
) {

    @KafkaListener(topics = ["accounts-stream"], groupId = "accounts")
    fun listenAccountsStream(account: AccountDto) {
        LOGGER.info { "=> new account was consumed $account" }
        accountService.save(account)
    }

//    test listeners for tasks
}
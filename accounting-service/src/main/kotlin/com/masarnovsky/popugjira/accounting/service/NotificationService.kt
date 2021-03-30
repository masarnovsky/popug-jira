package com.masarnovsky.popugjira.accounting.service

import mu.KotlinLogging
import org.springframework.stereotype.Service

private val LOGGER = KotlinLogging.logger {}

@Service
class NotificationService(
    val accountService: AccountService,
) {
    fun sendNotification(publicId: String, message: String) {
        val account = accountService.findByPublicId(publicId)
        LOGGER.info { "to: ${account.email}. Hi ${account.username}, $message" }
    }
}
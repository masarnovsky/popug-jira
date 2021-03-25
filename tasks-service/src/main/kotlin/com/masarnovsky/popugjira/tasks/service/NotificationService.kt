package com.masarnovsky.popugjira.tasks.service

import mu.KotlinLogging
import org.springframework.stereotype.Service

private val LOGGER = KotlinLogging.logger {}

@Service
class NotificationService(
    private val accountService: AccountService,
) {

    fun sendNotification(publicId: String, message:String) {
        val account = accountService.findByPublicId(publicId)
        LOGGER.info { "Hi ${account.username}, $message" }
    }
}
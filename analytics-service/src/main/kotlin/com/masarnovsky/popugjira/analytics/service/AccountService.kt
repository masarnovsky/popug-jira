package com.masarnovsky.popugjira.analytics.service

import com.masarnovsky.popugjira.analytics.model.Account
import com.masarnovsky.popugjira.analytics.repository.AccountRepository
import org.springframework.stereotype.Service


@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {

    fun save(account: Account): Account {
        return accountRepository.save(account)
    }
}
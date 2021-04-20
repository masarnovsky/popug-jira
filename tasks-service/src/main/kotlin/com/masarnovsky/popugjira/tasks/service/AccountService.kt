package com.masarnovsky.popugjira.tasks.service

import com.masarnovsky.popugjira.tasks.model.Account
import com.masarnovsky.popugjira.tasks.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {

    fun findAll(): List<Account> {
        return accountRepository.findAll()
    }

    fun findByPublicId(publicId: String): Account {
        return accountRepository.findByPublicId(publicId)
    }

    fun save(account: Account): Account {
        return accountRepository.save(account)
    }
}
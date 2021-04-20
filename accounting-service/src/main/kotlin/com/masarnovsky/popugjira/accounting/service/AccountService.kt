package com.masarnovsky.popugjira.accounting.service

import com.masarnovsky.popugjira.accounting.model.Account
import com.masarnovsky.popugjira.accounting.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {
    fun findByPublicId(publicId: String): Account {
        return accountRepository.findByPublicId(publicId)
    }

    fun save(account: Account): Account {
        return accountRepository.save(account)
    }
}
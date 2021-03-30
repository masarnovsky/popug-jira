package com.masarnovsky.popugjira.accounting.service

import com.masarnovsky.popugjira.accounting.model.Account
import com.masarnovsky.popugjira.accounting.model.AccountDto
import com.masarnovsky.popugjira.accounting.repository.AccountRepository
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

    fun save(account: AccountDto): Account {
        return save(
            Account(
                publicId = account.publicId, username = account.username,
                email = account.email, roles = account.roles,
                createdAt = account.createdAt, updatedAt = account.updatedAt
            )
        )
    }

    fun save(account: Account): Account {
        return accountRepository.save(account)
    }

    fun findAllAccounts(): List<Account> {
        return accountRepository.findAll()
    }

    fun clear() {
        accountRepository.deleteAll()
    }
}
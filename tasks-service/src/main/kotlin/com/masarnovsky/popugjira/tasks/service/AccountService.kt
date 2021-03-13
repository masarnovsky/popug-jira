package com.masarnovsky.popugjira.tasks.service

import com.masarnovsky.popugjira.tasks.model.Account
import com.masarnovsky.popugjira.tasks.model.AccountDto
import com.masarnovsky.popugjira.tasks.model.Role
import com.masarnovsky.popugjira.tasks.repository.AccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
        private val accountRepository: AccountRepository,
) {

    fun findAll(): List<Account> {
        return accountRepository.findAll()
    }

    fun findByPublicId(id: String): Account {
        return accountRepository.findByPublicId(UUID.fromString(id))
    }

    fun createAccount(account: AccountDto): Account {
        return accountRepository.save(Account(username = account.username, password = account.password, phoneNumber = account.phoneNumber, email = account.email, role = Role.valueOf(account.role)))
    }

    fun findAllAccounts(): List<Account> {
        return accountRepository.findAll()
    }

    fun clear() {
        accountRepository.deleteAll()
    }
}
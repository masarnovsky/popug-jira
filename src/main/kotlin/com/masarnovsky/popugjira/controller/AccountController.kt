package com.masarnovsky.popugjira.controller

import com.masarnovsky.popugjira.model.Account
import com.masarnovsky.popugjira.model.AccountDto
import com.masarnovsky.popugjira.service.AccountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/accounts")
class AccountController(
        private val accountService: AccountService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): Account {
        return accountService.findByPublicId(id)
    }

    @PostMapping
    fun createAccount(@RequestBody request: AccountDto): Account {
        return accountService.createAccount(request)
    }

    @GetMapping
    fun findAll(): List<Account> {
        return accountService.findAll()
    }

    @DeleteMapping
    fun clear() {
        accountService.clear()
    }
}
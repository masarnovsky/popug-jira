package com.masarnovsky.popugjira.auth.controller;

import com.masarnovsky.popugjira.auth.model.Account;
import com.masarnovsky.popugjira.auth.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authentication/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @PostMapping
    public Account save(@RequestBody Account account) {
        return accountService.save(account);
    }

    @GetMapping("/{publicId}")
    public Account findByPublicId(@PathVariable String publicId) {
        return accountService.findByPublicId(publicId);
    }
}

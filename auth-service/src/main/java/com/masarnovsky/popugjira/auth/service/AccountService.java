package com.masarnovsky.popugjira.auth.service;

import com.masarnovsky.popugjira.auth.model.Account;
import com.masarnovsky.popugjira.auth.model.Role;
import com.masarnovsky.popugjira.auth.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private static final String ACCOUNTS_STREAM_TOPIC = "accounts-stream";

    private final AccountRepository repository;
    private final KafkaTemplate<String, Account> kafkaTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Account save(Account account) {
        account.setObjectId(new ObjectId());
        account.setPublicId(UUID.randomUUID().toString());
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setRoles(account.getRoles() == null ? List.of(Role.EMPLOYEE) : account.getRoles());
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        Account newAccount = repository.save(account);

        kafkaTemplate.send(ACCOUNTS_STREAM_TOPIC, newAccount);
        LOGGER.info("New account was produced => {}", newAccount);

        return newAccount;
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Account findByPublicId(String publicId) {
        return repository.findByPublicId(publicId);
    }
}

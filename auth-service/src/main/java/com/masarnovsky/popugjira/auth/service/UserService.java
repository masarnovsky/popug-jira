package com.masarnovsky.popugjira.auth.service;

import com.masarnovsky.popugjira.auth.repository.UserRepository;
import com.masarnovsky.popugjira.auth.model.Account;
import com.masarnovsky.popugjira.auth.model.Role;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Account save(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        Role role = new Role();
        role.setRole("ADMIN");
        account.setRoles(Arrays.asList(role));
        account.setObjectId(new ObjectId());
        return repository.save(account);
    }

    public Account findUserByUsername(String username) {
        return repository.findByUsername(username);
    }
}

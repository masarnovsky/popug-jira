package com.masarnovsky.popugjira.auth.event;

import com.masarnovsky.popugjira.auth.model.Account;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class AccountCreatedEvent extends Event{
    private Account account;

    public AccountCreatedEvent(Account account) {
        super("AccountCreated");
        this.account = account;
    }
}

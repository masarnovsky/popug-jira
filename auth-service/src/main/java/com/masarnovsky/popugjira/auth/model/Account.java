package com.masarnovsky.popugjira.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class Account {
    private ObjectId objectId;
    private String username;
    private String password;
    private List<Role> roles;
}

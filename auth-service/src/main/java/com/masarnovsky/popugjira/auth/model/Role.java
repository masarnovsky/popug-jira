package com.masarnovsky.popugjira.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class Role {

    private ObjectId objectId;
    private int roleId;
    private String role;
}

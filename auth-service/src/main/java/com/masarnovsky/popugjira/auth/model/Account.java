package com.masarnovsky.popugjira.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class Account {
    @Id
    private ObjectId objectId;
    private String publicId;
    private String username;
    private String password;
    private String email;
    private List<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

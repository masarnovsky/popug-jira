package com.masarnovsky.popugjira.auth.repository;

import com.masarnovsky.popugjira.auth.model.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, ObjectId> {
    Account findByUsername(String username);
    Account findByPublicId(String publicId);
}

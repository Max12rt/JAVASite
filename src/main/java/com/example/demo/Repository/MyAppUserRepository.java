package com.example.demo.Repository;

import com.example.demo.Entity.MyAppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyAppUserRepository extends MongoRepository<MyAppUser, Long> {

    Optional<MyAppUser> findByUsername(String username);

}

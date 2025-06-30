package com.example.demo.Repository;
import com.example.demo.Entity.Wine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WineRepository extends MongoRepository<Wine, String> {
    Optional<Wine> findByName(String name);
    List<Wine> findByNameContainingIgnoreCase(String name);

}


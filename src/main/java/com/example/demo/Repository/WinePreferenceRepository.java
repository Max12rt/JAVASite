package com.example.demo.Repository;


import com.example.demo.Entity.WinePreference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WinePreferenceRepository extends MongoRepository<WinePreference, String> {
}


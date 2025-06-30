package com.example.demo.Repository;

import com.example.demo.Entity.AlcoholPreferenceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AlcoholPreferenceRepository extends MongoRepository<AlcoholPreferenceEntity, String> {
    List<AlcoholPreferenceEntity> findByUserId(String userId);
    List<AlcoholPreferenceEntity> findAllByUserId(String userId);

}

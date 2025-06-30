package com.example.demo.Repository;

import com.example.demo.Entity.Cocktail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends MongoRepository<Cocktail, Long> {
}

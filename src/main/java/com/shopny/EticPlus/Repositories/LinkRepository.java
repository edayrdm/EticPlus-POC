package com.shopny.EticPlus.Repositories;

import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Entities.Link;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends MongoRepository<Link,String> {
    Optional<Link> findByName(String name);
}

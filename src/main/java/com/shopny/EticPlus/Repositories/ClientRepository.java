package com.shopny.EticPlus.Repositories;

import com.shopny.EticPlus.Entities.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<Client,String> {

    Optional<Client> findByNameAndPassword(String name, String password);


}
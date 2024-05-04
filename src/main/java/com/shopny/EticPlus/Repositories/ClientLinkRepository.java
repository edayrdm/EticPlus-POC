package com.shopny.EticPlus.Repositories;

import com.shopny.EticPlus.Entities.ClientLink;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientLinkRepository extends MongoRepository<ClientLink,String> {

    Optional<List<ClientLink>> findByClientId(String clientId);

    Optional<ClientLink> findByClientIdAndLinkId(String ci, String li);

    void deleteByClientId(String id);
}
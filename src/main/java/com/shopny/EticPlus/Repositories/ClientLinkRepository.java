package com.shopny.EticPlus.Repositories;

import com.shopny.EticPlus.Entities.ClientLink;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientLinkRepository extends MongoRepository<ClientLink,String> {

    Optional<List<ClientLink>> findByClientId(String clientId);

    Optional<ClientLink> findByClientIdAndLinkId(String clientId, String linkId);

    void deleteByClientId(String clientId);

    @Query(value = "{clientId: ?0, statement: ?1}", count = true)
    public int countByClientIdAndStatement(String clientId, Boolean status);
}
package com.shopny.EticPlus.Repositories;

import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Entities.ClientLink;
import com.shopny.EticPlus.Entities.ClientLinkDto;
import com.shopny.EticPlus.Entities.Link;
import jakarta.annotation.Resource;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;


@Repository
public class CustomClientLinkRepositoryImp implements CustomClientLinkRepository {

    @Resource
    @Qualifier("productsMongoTemplate")
    MongoTemplate mongoTemplate;

    @Override
    public ClientLink updateClientLink(Client cl, Link link, Boolean st) {

        Query query= new Query().addCriteria(Criteria.where("clientId").is(cl.getId()).and("linkId").is(link.getId()));
        Update updateDefinition = new Update().set("statement", st);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

        return mongoTemplate.findAndModify(query, updateDefinition, options, ClientLink.class);
    }

    @Override
    public List<ClientLinkDto> findClientLinkUsingFindAndModify(Client cl) {

        LookupOperation lookup = LookupOperation.newLookup()
                .from("Link")
                .localField("ClientLink.linkId")
                .foreignField("Link._id")
                .as("join_link");

        ProjectionOperation projectToMatchModel = project()
                .andExpression("clientId").as("clientName")
                .andExpression("join_link.name").as("linkName")
                .andExpression("statement").as("statement");


        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("clientId").is(cl.getId())),
                lookup,
                Aggregation.unwind("join_link.name"),
                projectToMatchModel
        );

        List<ClientLinkDto> result = mongoTemplate.aggregate(aggregation, ClientLink.class, ClientLinkDto.class).getMappedResults();
        return result;
    }
}
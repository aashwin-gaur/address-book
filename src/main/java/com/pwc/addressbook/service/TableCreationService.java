package com.pwc.addressbook.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.pwc.addressbook.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static com.amazonaws.services.dynamodbv2.util.TableUtils.createTableIfNotExists;

@Service
@Profile("dev,test")
public class TableCreationService {

    @Autowired
    AmazonDynamoDB dynamoDB;

    @PostConstruct
    private void initialise() {
        createTables(User.class);
    }

    private void createTables(Class... tables) {
        for (Class clazz : tables) {

            List<KeySchemaElement> keySchema = Arrays
                    .asList(new KeySchemaElement()
                            .withAttributeName("id")
                            .withKeyType(KeyType.HASH));

            List<AttributeDefinition> attributeDefinitions = Arrays
                    .asList(new AttributeDefinition()
                            .withAttributeName("id")
                            .withAttributeType(ScalarAttributeType.S));

            CreateTableRequest request = new CreateTableRequest()
                    .withTableName(clazz.getSimpleName())
                    .withKeySchema(keySchema)
                    .withAttributeDefinitions(attributeDefinitions)
                    .withProvisionedThroughput(new ProvisionedThroughput()
                            .withReadCapacityUnits(1000L)
                            .withWriteCapacityUnits(1000L));

            createTableIfNotExists(dynamoDB, request);
        }
    }

}

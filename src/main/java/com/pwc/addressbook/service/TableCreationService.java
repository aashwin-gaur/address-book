package com.pwc.addressbook.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.pwc.addressbook.model.Friend;
import com.pwc.addressbook.model.User;
import com.pwc.addressbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.amazonaws.services.dynamodbv2.util.TableUtils.createTableIfNotExists;
import static com.amazonaws.services.dynamodbv2.util.TableUtils.waitUntilActive;

@Service
@Profile("dev")
public class TableCreationService {

    @Autowired
    AmazonDynamoDB dynamoDB;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    private void initialise() throws InterruptedException {
        if (createTable(User.class)) {
            seedData();
        }
    }

    private boolean createTable(Class clazz) throws InterruptedException {
        String tableName = clazz.getSimpleName();
        List<KeySchemaElement> keySchema = Arrays
                .asList(new KeySchemaElement()
                        .withAttributeName("id")
                        .withKeyType(KeyType.HASH));

        List<AttributeDefinition> attributeDefinitions = Arrays
                .asList(new AttributeDefinition()
                        .withAttributeName("id")
                        .withAttributeType(ScalarAttributeType.S));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(1000L)
                        .withWriteCapacityUnits(1000L));

        boolean created = createTableIfNotExists(dynamoDB, request);
        waitUntilActive(dynamoDB, tableName);
        return created;
    }

    private void seedData() {
        Friend f1 = new Friend("Sally", "1234567890");
        Friend f2 = new Friend("Joey", "1234567890");
        Friend f3 = new Friend("Marty", "1234567890");

        User u1 = new User();
        u1.setAddressBook(new HashSet<>(Arrays.asList(f1, f2, f3)));
        u1.setName("Joe");
        User u2 = new User();
        u2.setAddressBook(new HashSet<>(Arrays.asList(f1, f2, f3)));
        u2.setName("Sal");

        userRepository.saveAll(Arrays.asList(u1, u2));
    }

}

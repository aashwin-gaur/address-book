package com.pwc.addressbook;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.amazonaws.services.dynamodbv2.util.TableUtils.createTableIfNotExists;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractIntegrationTest {
    private static DynamoDBProxyServer server;

    @Autowired
    private AmazonDynamoDB dynamoDB;

    @BeforeClass
    public static void setupClass() throws Exception {
        String port = "8100";
        server = ServerRunner.createServerFromCommandLineArgs(
                new String[]{"-inMemory", "-port", port});
        server.start();
    }

    @AfterClass
    public static void teardownClass() throws Exception {
        server.stop();
    }

    void recreateTable(Class clazz) {
        String tableName = clazz.getSimpleName();
        DeleteTableRequest request = new DeleteTableRequest(tableName);
        if (TableUtils.deleteTableIfExists(dynamoDB, request)) {
            createTables(tableName);
        }
    }

    private void createTables(String... tables) {
        for (String tableName : tables) {

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

            createTableIfNotExists(dynamoDB, request);
        }
    }

}

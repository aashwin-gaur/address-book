package com.pwc.addressbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Data
@DynamoDBTable(tableName = "User")
public class User {

    @DynamoDBHashKey
    @DynamoDBGeneratedUuid(DynamoDBAutoGenerateStrategy.CREATE)
    private String id;

    @NotNull
    @NotBlank
    @DynamoDBAttribute
    private String name;


    @NotNull
    @NotEmpty
    @Valid
    private Set<Friend> addressBook;

}

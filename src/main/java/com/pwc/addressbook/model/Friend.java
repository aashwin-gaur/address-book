package com.pwc.addressbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@DynamoDBDocument
public class Friend {

    @NotNull @NotBlank
    String name;

    @NotNull @NotBlank
    String phoneNumber;
}

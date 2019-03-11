package com.pwc.addressbook.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(of = "name")
@AllArgsConstructor
@DynamoDBDocument
@NoArgsConstructor
public class Friend {

    @NotNull @NotBlank
    String name;

    @NotNull @NotBlank
    String phoneNumber;
}

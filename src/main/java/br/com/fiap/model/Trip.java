package br.com.fiap.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "trip")
public class Trip {
    @DynamoDBGeneratedUuid(DynamoDBAutoGenerateStrategy.CREATE)
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBRangeKey(attributeName = "dateTrip")
    private String dateTrip;
    @DynamoDBAttribute(attributeName = "country")
    private String country;
    @DynamoDBAttribute(attributeName = "city")
    private String city;
    @DynamoDBAttribute(attributeName = "url")
    private String url;
}

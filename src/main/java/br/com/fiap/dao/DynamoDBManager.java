package br.com.fiap.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBManager {

    private static DynamoDBMapper mapper;

    static {
        AmazonDynamoDB ddb = null;
        ddb = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();
        mapper = new DynamoDBMapper(ddb);
    }

    public static DynamoDBMapper mapper() {
        return DynamoDBManager.mapper;
    }

}

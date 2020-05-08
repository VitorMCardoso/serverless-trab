package br.com.fiap.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import br.com.fiap.dao.DynamoDBManager;
import br.com.fiap.model.Trip;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripRepository {
    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

    public Trip save(final Trip trip) {
        System.out.println("TRIP => " + trip);
        mapper.save(trip);
        return trip;
    }

    public PaginatedScanList<Trip> findById(String id) {
        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(id));
        final DynamoDBScanExpression tripDynamoDBScanExpression = new DynamoDBScanExpression()
                .withFilterExpression("id = :val1").withExpressionAttributeValues(eav);
        PaginatedScanList<Trip> trip = mapper.scan(Trip.class, tripDynamoDBScanExpression);
        return trip;
    }

    public PaginatedScanList<Trip> findByPeriod(String start, String end) {
        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(start));
        eav.put(":val2", new AttributeValue().withS(end));
        final DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
                .withFilterExpression("dateTrip >= :val1 and dateTrip <= :val2 ").withExpressionAttributeValues(eav);
        final PaginatedScanList<Trip> trips = mapper.scan(Trip.class, queryExpression);
        return trips;
    }
}

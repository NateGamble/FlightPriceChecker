package com.nategamble.flight_price_checker;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "FlightTracking")
public class Flight {
    @DynamoDBHashKey
    private String flightId;
    
    @DynamoDBAttribute
    private String origin;
    
    @DynamoDBAttribute
    private String destination;
    
    @DynamoDBAttribute
    private Double latestPrice;
    
    @DynamoDBAttribute
    private String userEmail;
}
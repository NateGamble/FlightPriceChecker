package com.nategamble.flight_price_checker;

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
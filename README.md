# FlightPriceChecker
A project to work with Java, Spring, and AWS. Checks prices of flights and notifies user of changes.


### ✈️ **Flight Price Tracker – Full Breakdown**  
A **Flight Price Tracker** is a Java + Spring Boot application that monitors flight prices and alerts users when the price drops. It uses **AWS Always Free Tier** services to ensure no cost while demonstrating backend development, database management, serverless functions, and notifications.  

---

## **📌 Features & Requirements**  

### 🏗️ **Core Features:**  
1. **User Management:**  
   - Users sign up and log in.  
   - Users add flight routes they want to track (e.g., LAX → JFK).  

2. **Flight Price Tracking:**  
   - Fetches real-time flight prices from an external API or web scraping.  
   - Stores flight price history to track trends.  

3. **Alerts & Notifications:**  
   - Users receive an email or SMS when the price drops.  
   - Can send daily/weekly price update reports.  

4. **Admin Dashboard (Optional):**  
   - Displays analytics (e.g., most tracked routes, price trends).  

---

## **🛠️ Tech Stack & AWS Services**  

### 🔹 **Backend:**  
- **Java + Spring Boot** (Spring Web, Spring Data JPA, Spring Security, Spring Scheduler)  
- **Spring Scheduler** → Runs periodic price checks  

### 🔹 **Database:**  
- **AWS DynamoDB** (Always Free 25GB)  
  - Stores user-tracked flights & price history  
  - Fast, scalable NoSQL for querying price trends  

### 🔹 **Flight Price Scraper:**  
- **AWS Lambda** (Always Free 1M requests/month)  
  - Fetches flight data periodically  
  - Runs web scraping (if API isn’t available)  

### 🔹 **API & Serverless Execution:**  
- **AWS API Gateway** (Always Free 1M requests/month)  
  - Exposes REST API endpoints  

### 🔹 **Notifications & Alerts:**  
- **AWS SNS (Simple Notification Service)** (Always Free 1M notifications/month)  
  - Sends email/SMS price drop alerts  

- **AWS SES (Simple Email Service)** (Always Free 3,000 emails/month)  
  - Used for user email notifications  

---

## **📖 System Architecture**  

```
+--------------------+
|  User Interface   |  (React/Angular, optional)
+--------------------+
        ↓ REST API
+----------------------------+
| Spring Boot Backend        |
| (User Management, Flights) |
+----------------------------+
        ↓ Queries
+----------------------+
| AWS DynamoDB        |  (Stores user flights & price history)
+----------------------+
        ↓ Lambda Calls
+------------------------------+
| AWS Lambda (Flight Scraper)  |
|  - Fetches flight prices     |
|  - Saves to DynamoDB         |
+------------------------------+
        ↓ Alerts
+-------------------------+
| AWS SNS / AWS SES       |  (Sends email/SMS alerts)
+-------------------------+
```

---

## **🛠️ Step-by-Step Development Plan**  

### 📌 **Step 1: Set Up Backend with Spring Boot**  
- Create a **Spring Boot** project using **Spring Initializr**  
- Add dependencies:  
  - `Spring Web` (for REST API)  
  - `Spring Data JPA` (for database interaction)  
  - `Spring Security` (for user authentication)  
  - `Spring Scheduler` (for periodic price checks)  

---

### 📌 **Step 2: Set Up AWS DynamoDB for Storage**  
- Create a DynamoDB table:  
  - **Table Name:** `FlightTracking`  
  - **Primary Key:** `flightId` (UUID)  
  - **Attributes:**  
    - `origin` (String, e.g., "LAX")  
    - `destination` (String, e.g., "JFK")  
    - `latest_price` (Number)  
    - `user_email` (String)  

- Implement a **DynamoDB repository** in Spring Boot using AWS SDK:  
  ```java
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
  ```

---

### 📌 **Step 3: Implement AWS Lambda for Flight Price Scraping**  
- Write a **Lambda function** in Java to fetch flight prices via an API (or web scraping).  
- Deploy the function to AWS Lambda.  
- Schedule it to run every 6 hours using **AWS EventBridge**.  

**Example Lambda Function:**  
```java
public class FlightPriceFetcher implements RequestHandler<Object, String> {
    @Override
    public String handleRequest(Object input, Context context) {
        String apiUrl = "https://api.example.com/flights?origin=LAX&destination=JFK";
        Double price = fetchFlightPrice(apiUrl);
        saveToDynamoDB("LAX", "JFK", price);
        return "Price updated: $" + price;
    }
}
```

---

### 📌 **Step 4: Set Up Notifications Using AWS SNS & SES**  
- Create an **SNS Topic** (e.g., `FlightPriceAlerts`)  
- Subscribe users to the topic  
- Configure **SES** to send email alerts  

**Spring Boot Integration with AWS SNS:**  
```java
@Autowired
private AmazonSNS amazonSNS;

public void sendPriceAlert(String email, String message) {
    amazonSNS.publish(new PublishRequest("FlightPriceAlerts", message, "Price Alert: " + email));
}
```

---

### 📌 **Step 5: Expose REST API via AWS API Gateway**  
- Create REST endpoints for users to:  
  - **Track a flight** (`POST /flights`)  
  - **View tracked flights** (`GET /flights`)  
  - **Unsubscribe from alerts** (`DELETE /flights/{id}`)  

**Example Controller:**  
```java
@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<String> trackFlight(@RequestBody Flight flight) {
        flightService.addFlight(flight);
        return ResponseEntity.ok("Flight tracking added!");
    }
}
```

---

## **🚀 Bonus Features** (For Extra Resume Impact)  
✅ **Price Trend Graphs** (Use AWS QuickSight for visualization)  
✅ **User Preferences for Notification Thresholds** (e.g., only notify if price drops 10%+)  
✅ **Historical Price Predictions** (Use AWS Lambda for machine learning predictions)  

---

## **📊 Summary**  
| Feature               | Technology Used             |
|-----------------------|---------------------------|
| Backend API          | Java + Spring Boot         |
| Database             | AWS DynamoDB (Free Tier)   |
| Flight Price Scraper | AWS Lambda + API Gateway  |
| Notifications        | AWS SNS + SES             |
| Authentication       | Spring Security           |

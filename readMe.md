# Financial Tracker

A Spring Boot application to track financial transactions, implemented using JDBC and an H2 in-memory database for easy setup and testing.

## Features

- **CRUD Operations:** Manage financial transactions with full create, read, update, and delete capabilities.
- **RESTful API:** Easy-to-use API endpoints for interaction with the financial transactions data.
- **In-Memory Database:** Utilizes H2 database for development ease, without the need for external database setup.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- JDK 11 or newer
- Maven 3.2 or newer

### Installation

1. Clone the repository:
   git clone https://github.com/hunterryan0000/financial-tracker.git
2. Navigate to the project directory:
   cd financial-tracker
3. Run the application:
   ./mvnw spring-boot:run
4. Access the H2 Database Console at http://localhost:8080/h2-console. Use the JDBC URL, username, and password as configured in your application.properties.

## API Endpoints

- POST /transactions - Create a new financial transaction.
- GET /transactions - Retrieve all financial transactions.
- GET /transactions/{id} - Retrieve a financial transaction by its ID.
- DELETE /transactions/{id} - Delete a financial transaction by its ID.

## Contributing

Contributions are welcome. Please open an issue to discuss your idea or submit a pull request.
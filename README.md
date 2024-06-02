# Binarfud API

## Description
Binarfud API is an individual project developed for the Synrgy Bootcamp Back End Java course. This API provides basic CRUD operations for managing an e-commerce platform. The project is built using the Java Spring Framework and is designed to demonstrate key concepts in backend development.

## Installation

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven
- Git
- PostgreSQL

### Steps
1. **Clone the repository**
    ```sh
    git clone https://github.com/Chelle007/binarfud-api.git
    cd binarfud-api
    ```

2. **Set up the database**
    - Create a PostgreSQL database named `binarfud`.
    - Update the database connection settings in `src/main/resources/application.properties` if necessary.

3. **Build the project**
    ```sh
    mvn clean install
    ```

4. **Run the application**
    ```sh
    mvn spring-boot:run
    ```

5. **Access the application**
    - The API will be running at: `http://localhost:8080`
    - Swagger UI can be accessed at: `http://localhost:8080/swagger-ui/index.html`

## Usage

### Base URL
The base URL for all API requests during local development is: `http://localhost:8080`

### Using Swagger UI
Swagger UI is an interactive tool that allows you to explore and test the API endpoints with ease. It provides comprehensive documentation and lets you try out the endpoints directly from the browser.

#### Accessing Swagger UI
You can access the Swagger UI for Binarfud API at the following URL: `http://localhost:8080/swagger-ui/index.html`

## Deployment on Railway

### Accessing the Deployed API
You can access the deployed Binarfud API on Railway at the following URL: `https://binarfud-api-production.up.railway.app`

### API Documentation
The API documentation remains the same as during local development. You can explore and interact with the endpoints using Swagger UI, which is accessible at the deployed URL: `https://binarfud-api-production.up.railway.app/login/swagger-ui/index.html`

### Additional Notes
To access Swagger UI, you must log in first using the following credentials:
Username: seller
Password: 12345678

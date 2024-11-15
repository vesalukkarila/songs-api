
# songs-api
My very first RESTful API for managing songs, implemented using **plain Spring features without Spring Boot**.  
This project provides CRUD functionality for song records, utilizing Spring MVC, Spring Validation, and a custom in-memory database (H2).

## Technologies Used

- Java 19
- Spring MVC
- H2 Database
- Hibernate Validator
- JUnit 5 and Mockito for testing
- Docker
- GitHub Actions (for CI/CD to automatically deploy documentation to GitHub Pages)


## Getting Started

To run the application, you have two choices:
1. Run locally
2. Run in Docker

### Locally

1. Ensure all the dependencies are installed
2. Clone the repository  
3. Build the project using Maven: 
    ```sh
    mvn clean install
    ```
4. Run the application with: 
    ```sh
    java -jar target/songsapi-1.0-SNAPSHOT.jar
    ```

### Run in Docker
1. Build the image:
    ```shell
    docker build . -t songs-api:latest     
    ```
2. Run the image:
    ```shell
    docker run -it -p 8080:8080 songs-api:latest 
    ```
3. Open the api in http://localhost:8080

## Documentation
Since this project is developed in plain Spring (which doesn't seem to natively support Swagger),  
the OpenAPI specification has been **manually** written to describe the API endpoints, request bodies, responses, and error handling.  
Documentation is hosted at github pages:  
https://vesalukkarila.github.io/songs-api/



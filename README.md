
# songs-api
A RESTful API for managing songs, implemented using plain Spring features without Spring Boot.  
This project provides CRUD functionality for song records, utilizing Spring MVC, Spring Validation, and a custom in-memory database (H2).

## Technologies Used

- Java 19
- Spring MVC
- H2 Database
- Hibernate Validator
- JUnit 5 and Mockito for testing

## Getting Started

To run the application:

1. Clone the repository  
2. Build the project using Maven: 
```sh
mvn clean install
```
3. Run the application with: 
```sh
java -jar target/songsapi-1.0-SNAPSHOT.jar
```


## Endpoints

<details>
  <summary><code>GET</code> <code><b>/</b></code> <code>(Returns greeting message from the Songs API)</code></summary>
 
### Request
```sh
curl http://localhost:8080/ -i
```

### Response
- **Status code:** 200 OK   
- **Content-Type:** application/json

```json
{
    "message": "Hello from Songs API"
}
```
</details>


<details>
  <summary><code>GET</code> <code><b>/songs</b></code> <code>(Retrieves a list of all Songs)</code></summary>

 ### Request
```sh
curl http://localhost:8080/songs -i
```
### Response
- **Status code:** 200 OK  
- **Content-Type:** application/json
- **Response body:** Before any user POST's, example:
```json

[
    {
        "id": "5e57a1ca-9dc4-4a0c-ab61-8875d133dd49",
        "name": "Thunderstruck",
        "artist": "AC/DC",
        "publishYear": 1990
    }
]

```
</details>


<details>
  <summary><code>POST</code> <code><b>/songs</b></code> <code>(Creates a new Song)</code></summary>
 
### Request
```sh
curl -X POST http://localhost:8080/songs \
-H "Content-Type: application/json" \
-d '{
  "name": "The Thrill Is Gone",
  "artist": "B.B. King",
  "publish_year": 1969
}' -i

```
- **Request body:** All fields required, example:
```json
{
    "name" : "The Thrill is Gone",
    "artist" : "B.B. King",
    "publish_year" : 1969
}
```


### Success response
- **Status code**: 201 CREATED  
- **Content-Type**: application/json 
- **Location**: /songs/ff88ef0c-8a9b-43eb-9de2-ad8fcd82f252
- **Response body:** 
```json
{
    "id": "ff88ef0c-8a9b-43eb-9de2-ad8fcd82f252",
    "name": "The Thrill is Gone",
    "artist": "B.B. King",
    "publish_year": 1969
}
```

### Error responses
- **Status code**: 400 BAD REQUEST
- **Content-Type**: application/json 
- **Response body:** Example shows all possible validation error messages
```json
{
    "message": "Validation error(s) in your request",
    "errors": {
        "publish_year": "must not be null",
        "artist": "must not be blank",
        "name": "must not be blank"
        "publish_year": "publish_year must be between 1889 and the current year."
    }
}
```

#### OR

- **Status code**: 409 CONFLICT  
- **Content-Type**: application/json 
- **Response body:** if trying to add duplicate of an existing Song

```json
{
    "message": "Song with provided name(The Thrill is Gone), artist(B.B. King) and publish_year(1969) already exists."
}
```



</details>


<details>
  <summary><code>PUT</code> <code><b>/songs/{id}</b></code> <code>(Updates an existing Song)</code></summary>

### Request
- **Path variable:** id(string), the unique identifier of the Song to be updated (UUID).
```sh
curl -X PUT http://localhost:8080/songs/ff88ef0c-8a9b-43eb-9de2-ad8fcd82f252 \
-H "Content-Type: application/json" \
-d '{
  "name": "The Thrill Is NOT  Gone",
  "artist": "B.B. King",
  "publish_year": 1969
}' -i

```
- **Request body:** All fields required, example:
```json
{
    "name" : "The Thrill is NOT Gone",
    "artist" : "B.B. King",
    "publish_year" : 1969
}
```
### Success response
- **Status code**: 200 OK
- **Content-Type**: application/json 
- **Response body:**
```json
{
    "id": "ff88ef0c-8a9b-43eb-9de2-ad8fcd82f252",
    "name": "The Thrill is NOT Gone",
    "artist": "B.B. King",
    "publish_year": 1969
}
```


### Error responses

- **Status code**: 400 BAD REQUEST
- **Content-Type**: application/json 
- **Response body:** Example shows all possible validation error messages
```json
{
    "message": "Validation error(s) in your request",
    "errors": {
        "publish_year": "must not be null",
        "artist": "must not be blank",
        "name": "must not be blank"
        "publish_year": "publish_year must be between 1889 and the current year."
    }
}
```

#### OR

- **Status code**: 400 BAD REQUEST
- **Content-Type**: application/json
- **Response body:** if provided id is not valid UUID
```json
{
    "message": "Given identifier (273f842d-6307-476d-8ba2-7c215a0290a) is invalid. Expected format: 8-4-4-4-12 hex."
}
```

#### OR

- **Status code**: 404 NOT FOUND
- **Content-Type**: application/json 
- **Response body:** if Song with provided id not found in database
```json
{
    "message": "Song with id 594e9937-25c5-47a5-8871-80598e116273 was not found"
}
```

#### OR

- **Status code**: 409 CONFLICT  
- **Content-Type**: application/json 
- **Response body:** if trying to update a duplicate of an existing Song
```json
{
    "message": "Song with provided name(The Thrill is NOT Gone), artist(B.B. King) and publish_year(1969) already exists."
}
```
</details>


<details>
  <summary><code>PATCH</code> <code><b>/songs/{id}</b></code> <code>(Partially updates an existing Song)</code></summary>

### Request
- **Path variable:** id(string), the unique identifier of the Song to be partially updated (UUID).

```sh
curl -X PATCH http://localhost:8080/songs/ff88ef0c-8a9b-43eb-9de2-ad8fcd82f252 \
-H "Content-Type: application/json" \
-d '{
  "name": "The Thrill Is Gone AGAIN",
  "artist": "B.B. King",
  "publish_year": 1969
}' -i

```
- **Request body:** Only one field required, example:
```json
{
    "name" : "The Thrill is Gone AGAIN"
}
```
### Success response
- **Status code**: 200 OK
- **Content-Type**: application/json 
- **Response body:**
```json
{
    "id": "ff88ef0c-8a9b-43eb-9de2-ad8fcd82f252",
    "name": "The Thrill is Gone AGAIN",
    "artist": "B.B. King",
    "publish_year": 1969
}
```
### Error responses
- **Status code**: 400 BAD REQUEST
- **Content-Type**: application/json 
- **Response body:** Example shows all possible validation error messages(only 1 field required)
```json
{
    "message": "Validation error(s) in your request",
    "errors": {
        "publish_year": "must not be null",
        "artist": "must not be blank",
        "name": "must not be blank"
        "publish_year": "publish_year must be between 1889 and the current year."
    }
}
```

#### OR
- **Status code**: 400 BAD REQUEST
- **Content-Type**: application/json 
- **Response body:** if provided id is not valid UUID
```json
{
    "message": "Given identifier (273f842d-6307-476d-8ba2-7c215a0290a) is invalid. Expected format: 8-4-4-4-12 hex."
}
```

#### OR

- **Status code**: 404 NOT FOUND
- **Content-Type**: application/json 
- **Response body:** if Song with provided id not found in database
```json
{
    "message": "Song with id 594e9937-25c5-47a5-8871-80598e116273 was not found"
}
```

#### OR

- **Status code**: 409 CONFLICT  
- **Content-Type**: application/json 
- **Response body:** if trying to update a duplicate of an existing Song
```json
{
    "message": "Song with provided name(The Thrill is NOT Gone), artist(B.B. King) and publish_year(1969) already exists."
}
```
</details>


<details>
  <summary><code>DELETE</code> <code><b>/songs/{id}</b></code> <code>(Deletes a Song)</code></summary>

### Request
- **Path variable:** id(string), the unique identifier of the Song to be deleted (UUID).
```sh
curl -X DELETE http://localhost:8080/songs/ff88ef0c-8a9b-43eb-9de2-ad8fcd82f252 -H \
"Content-Type: application/json" -i

```
### Success response
- **Status code**: 204 NO CONTENT
- **Content-Type**: application/json 
- **Response body:** None
  
### Error responses
- **Status code**: 400 BAD REQUEST
- **Content-Type**: application/json 
- **Response body:** if provided id is not valid UUID
```json
{
    "message": "Given identifier (273f842d-6307-476d-8ba2-7c215a0290a) is invalid. Expected format: 8-4-4-4-12 hex."
}
```

#### OR

- **Status code**: 404 NOT FOUND
- **Content-Type**: application/json 
- **Response body:** if Song with provided id not found in database
```json
{
    "message": "Song with id 594e9937-25c5-47a5-8871-80598e116273 was not found"
}
```

</details>

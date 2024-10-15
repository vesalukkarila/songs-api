
# songs-api
REST API excercise with Spring Core features without Spring Boot. 
- TODO: fix descriptions, add:run with docker


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
  <summary><code>GET</code> <code><b>/songs</b></code> <code>(Retrieves a list of all songs)</code></summary>

 ### Request
```sh
curl http://localhost:8080/songs -i
```
### Response
- **Status code:** 200 OK  
- **Content-Type:** application/json
- **Response body:** Example, before any user POST's:
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
- **Status code**: 409 CONFLICT  
- **Content-Type**: application/json 
- **Response body:** 

```json
{
    "error": "Song with name: The Thrill is Gone, artist: B.B. King and publishYear: 1969 already exists."
}
```

#### OR
- **Status code**: 400 BAD REQUEST
- **Content-Type**: application/json 
- **Response body:** if name not provided
```json
{
    "name": "must not be blank"
}
```
- **Response body:** if artist not provided
```json
{
    "name": "must not be blank"
}
```
- **Response body:** if year not provided
```json
{
  "publish_year": "must not be null"
}
```

- **Response body:** if publish_year not within range  
```json
{
    "publish_year": "Year must be between 1889 and the current year."
}
```
</details>


